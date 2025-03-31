package com.wzy.quanyoumall.ware.service.impl;

import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.constant.WareConstant;
import com.wzy.quanyoumall.common.exception.OutOfStockException;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskEntity;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import com.wzy.quanyoumall.ware.feign.MemberFeignService;
import com.wzy.quanyoumall.ware.mapper.WareSkuMapper;
import com.wzy.quanyoumall.ware.service.WareOrderTaskDetailService;
import com.wzy.quanyoumall.ware.service.WareOrderTaskService;
import com.wzy.quanyoumall.ware.service.WareSkuService;
import com.wzy.quanyoumall.ware.vo.FareVo;
import com.wzy.quanyoumall.ware.vo.MemberAddressVo;
import com.wzy.quanyoumall.ware.vo.OrderItemVo;
import com.wzy.quanyoumall.ware.vo.WareSkuLockVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSkuEntity> implements WareSkuService {

    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private WareOrderTaskService wareOrderTaskService;
    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Page<WareSkuEntity> queryPage(WareSkuEntity wareSkuEntity, Page<WareSkuEntity> page) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(wareSkuEntity)) {
            if (wareSkuEntity.getWareId() != null) {
                queryWrapper.eq("ware_id", wareSkuEntity.getWareId());
            }
            if (wareSkuEntity.getSkuId() != null) {
                queryWrapper.eq("sku_id", wareSkuEntity.getSkuId());
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<SkuStockTO> getStokeBySkuIds(List<Long> skuIds) {
        return baseMapper.getStokeBySkuIds(skuIds);
    }

    @Override
    public List<SkuStockTO> getHasStokeBySkuIds(List<Long> skuIds) {
        List<SkuStockTO> skuStockTOS = baseMapper.getStokeBySkuIds(skuIds);
        skuStockTOS.forEach(i -> {
            if (i.getStockSum() > 0) {
                i.setHasStock(true);
            }
        });
        return skuStockTOS;
    }

    @Override
    public FareVo getFareByAddrId(Long addrId) {
        R info = memberFeignService.info(addrId);
        MemberAddressVo addressVo = info.getData("data", new TypeReference<MemberAddressVo>() {
        });
        int i = (int) (addressVo.getId() % 10);
        BigDecimal fare = new BigDecimal(i);
        FareVo fareVo = new FareVo();
        fareVo.setFare(fare);
        fareVo.setAddress(addressVo);
        return fareVo;
    }

    @Override
    @Transactional
    public boolean lockStockByLockVo(WareSkuLockVo lockVo) throws OutOfStockException {
        boolean flag = true;
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(lockVo.getOrderSn());
        wareOrderTaskEntity.setOrderId(lockVo.getOrderId());
        wareOrderTaskService.save(wareOrderTaskEntity);
        List<OrderItemVo> locks = lockVo.getLocks();
        for (OrderItemVo lock : locks) {
            boolean singleFlag = false;
            List<Long> wareIds = this.listGetWareSkuIdByArgs(lock.getSkuId(), lock.getCount());
            for (Long wareId : wareIds) {
                int i = baseMapper.lockStockByItem(lock.getSkuId(), lock.getCount(), wareId);
                if (i == 1) {
                    this.createTaskDetail(wareOrderTaskEntity.getId(), lock.getSkuId(), lock.getCount(), wareId);
                    singleFlag = true;
                    break;
                }
            }
            if (!singleFlag) {
                // 手动标记回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                flag = false;
                break;
            }
        }
        rabbitTemplate.convertAndSend("stock.event.exchange", "stock.finish", wareOrderTaskEntity);
        return flag;
    }

    /**
     * 创建并保存库存工作单详情
     *
     * @param taskId
     * @param skuId
     * @param count
     * @param wareId
     * @return
     */
    private void createTaskDetail(Long taskId, Long skuId, Integer count, Long wareId) {
        WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
        wareOrderTaskDetailEntity.setTaskId(taskId);
        wareOrderTaskDetailEntity.setSkuId(skuId);
        wareOrderTaskDetailEntity.setSkuNum(count);
        wareOrderTaskDetailEntity.setWareId(wareId);
        wareOrderTaskDetailEntity.setLockStatus(WareConstant.OrderTaskDetailStatusEnum.LOCKED.getCode());
        wareOrderTaskDetailService.save(wareOrderTaskDetailEntity);
    }

    @Override
    public List<Long> listGetWareSkuIdByArgs(Long skuId, Integer num) {
        List<Long> wareIds = baseMapper.listGetWareSkuIdByArgs(skuId, num);
        return wareIds;
    }

    @Override
    public void rollbackStock(List<WareOrderTaskDetailEntity> taskDetailEntityList) {
        List<WareOrderTaskDetailEntity> taskDetailList = taskDetailEntityList.stream()
                .filter(item -> WareConstant.OrderTaskDetailStatusEnum.LOCKED.getCode() == item.getLockStatus())
                .map(item -> {
                    int i = this.rollbackSingle(item);
                    if (i == 1) {
                        item.setLockStatus(WareConstant.OrderTaskDetailStatusEnum.UNLOCKED.getCode());
                    }
                    return item;
                }).collect(Collectors.toList());
        wareOrderTaskDetailService.updateBatchById(taskDetailList);
    }

    /**
     * 解锁锁定的库存
     *
     * @param wareOrderTaskDetailEntity
     * @return
     */
    @Override
    public int rollbackSingle(WareOrderTaskDetailEntity wareOrderTaskDetailEntity) {
        return baseMapper.rollbackSingle(wareOrderTaskDetailEntity);
    }
}