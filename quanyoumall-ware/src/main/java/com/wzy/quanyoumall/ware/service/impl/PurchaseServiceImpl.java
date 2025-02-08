package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzy.quanyoumall.common.constant.WareConstant;
import com.wzy.quanyoumall.common.to.SkuDTO;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.Dto.PurchaseFinshDTO;
import com.wzy.quanyoumall.ware.Dto.PurchaseFinshDetailDTO;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;
import com.wzy.quanyoumall.ware.entity.PurchaseEntity;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import com.wzy.quanyoumall.ware.feign.ProductFeignService;
import com.wzy.quanyoumall.ware.mapper.PurchaseMapper;
import com.wzy.quanyoumall.ware.service.PurchaseDetailService;
import com.wzy.quanyoumall.ware.service.PurchaseService;
import com.wzy.quanyoumall.ware.service.WareSkuService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private WareSkuService wareSkuService;
    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public Page<PurchaseEntity> queryPage(PurchaseEntity purchaseEntity, Page<PurchaseEntity> page) {
        QueryWrapper<PurchaseEntity> qw = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(purchaseEntity)) {
            if (purchaseEntity.getStatus() != null) {
                qw.eq("status", purchaseEntity.getStatus());
            }
            if (purchaseEntity.getWareId() != null) {
                qw.eq("ware_id", purchaseEntity.getWareId());
            }
            if (purchaseEntity.getId() != null) {
                qw.eq("id", purchaseEntity.getId());
            }
        }
        return baseMapper.selectPage(page, qw);
    }

    @Override
    public List<PurchaseEntity> queryListByCondition(String checkStatus) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(checkStatus)) {
            queryWrapper.in("status", checkStatus);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public R contactPurchaseDetail(List<Long> purchaseDetailIdList, Long purchaseId) {
        PurchaseEntity purchaseEntity = null;
        List<PurchaseDetailEntity> detailEntityList = purchaseDetailService.listByIds(purchaseDetailIdList);
        List<PurchaseDetailEntity> errList = detailEntityList.stream().filter(item -> {
            if (item.getStatus() != WareConstant.PurchaseDetailStatusEnum.CREATED.getCode()
                    || item.getStatus() != WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (errList.size() > 0) {
            return R.error("采购需求状态异常,只有新建和已分配的可以合并");
        }
        if (purchaseId == null) {
            purchaseEntity = new PurchaseEntity();
            purchaseEntity.setPriority(1);
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            baseMapper.insert(purchaseEntity);
        } else {
            purchaseEntity = baseMapper.selectById(purchaseId);
        }
        this.realContact(detailEntityList, purchaseEntity);
        return R.ok();
    }

    @Override
    @Transactional
    public void receivePurchaseByPurchaseIds(List<Long> purchaseIds) {
        List<PurchaseEntity> purchaseEntities = this.listByIds(purchaseIds);
        // 领取采购单的list查询接口应该根据用户id和采购单状态查，不应在此过滤，能提交的都是此用户可以领取或分配给此用户的。
        // 二次校验并修改状态。
        List<PurchaseEntity> realCanReceivePurchaseList = purchaseEntities.stream().filter(item -> {
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode()
                    || item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
        }).map(item -> {
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            return item;
        }).collect(Collectors.toList());
        //TODO:如果不是已分配的采购单,先分配给领取人.
        // 框架原接口 /sys/user/info 返回空值，无法获取
        // 写了一个通过token查询然后返回的接口，调用时报Could not extract response: no suitable HttpMessageConverter found for response type [class com.wzy.quanyoumall.common.utils.R] and content type [application/octet-stream]
        // 暂无法解决,搁置.后期shiro改为spring security时一并解决.
//        R userinfo = renrenFastFeignService.info();
//        SysUserTO uesr = JSONObject.parseObject(userinfo.get("uesr").toString(), SysUserTO.class);
        // 修改采购单关联的采购项的状态
        List<Long> realPurchaseIds = realCanReceivePurchaseList.stream().map(PurchaseEntity::getId).collect(Collectors.toList());
        purchaseDetailService.changeStatusByPurchaseIds(realPurchaseIds, WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
        // 更新数据
        this.updateBatchById(realCanReceivePurchaseList);
    }

    @Override
    @Transactional
    public void finshPurchase(PurchaseFinshDTO purchaseFinshDTO) {
        PurchaseEntity purchaseEntity = this.getById(purchaseFinshDTO.getPurchaseId());
        // 修改采购单状态
        purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISH.getCode());
        List<PurchaseFinshDetailDTO> finshDetailDTOList = purchaseFinshDTO.getFinshDetailDTOList();
        List<PurchaseDetailEntity> finshPurchaseDetailEntityList = new ArrayList<>();
        finshDetailDTOList.forEach(item -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            BeanUtils.copyProperties(item, purchaseDetailEntity);
            finshPurchaseDetailEntityList.add(purchaseDetailEntity);
        });
        // 查看是否有异常数据
        List<PurchaseDetailEntity> errEntityList = finshPurchaseDetailEntityList.stream().filter(item -> StringUtils.isNotBlank(item.getReason())).collect(Collectors.toList());
        if (errEntityList.size() > 0) {
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
        // 更新数据
        purchaseDetailService.updateBatchById(finshPurchaseDetailEntityList);
        baseMapper.updateById(purchaseEntity);
        // 更新库存
        List<WareSkuEntity> updateWareSkuEntitieList = new ArrayList<>();
        finshPurchaseDetailEntityList.forEach(item -> {
            // 如果没有库存新增,如果有库存修改
            Long skuId = item.getSkuId();
            List<WareSkuEntity> wareSkuEntities = wareSkuService.list((new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", item.getWareId())));
            WareSkuEntity wse = null;
            if (wareSkuEntities.size() > 0) {
                wse = wareSkuEntities.get(0);
                wse.setStock(wse.getStock() + item.getSkuNum());
            } else {
                wse = new WareSkuEntity();
                R res = productFeignService.info(item.getSkuId());
                SkuDTO skuDTO = new ObjectMapper().convertValue(res.get("skuInfo"), SkuDTO.class);
                wse.setSkuName(skuDTO.getSkuName());
                wse.setSkuId(item.getSkuId());
                wse.setWareId(item.getWareId());
                wse.setStock(item.getSkuNum());
                wse.setStockLocked(0);
            }
            updateWareSkuEntitieList.add(wse);
        });
        wareSkuService.saveOrUpdateBatch(updateWareSkuEntitieList);
    }

    public void realContact(List<PurchaseDetailEntity> detailEntityList, PurchaseEntity purchaseEntity) {
        Long purchaseId = purchaseEntity.getId();
        detailEntityList.forEach(detailEntity -> {
            detailEntity.setPurchaseId(purchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
        });
        // 关联采购单
        purchaseDetailService.updateBatchById(detailEntityList);
        // 计算总金额
        BigDecimal sumPrice = detailEntityList.stream()
                .map(PurchaseDetailEntity::getSkuPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseEntity.setAmount(purchaseEntity.getAmount() != null ? purchaseEntity.getAmount().add(sumPrice) : sumPrice);
        purchaseEntity.setWareId(detailEntityList.get(0).getWareId());
        // 更新采购单.
        baseMapper.updateById(purchaseEntity);
    }
}