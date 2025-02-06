package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.constant.WareConstant;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;
import com.wzy.quanyoumall.ware.entity.PurchaseEntity;
import com.wzy.quanyoumall.ware.mapper.PurchaseMapper;
import com.wzy.quanyoumall.ware.service.PurchaseDetailService;
import com.wzy.quanyoumall.ware.service.PurchaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

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
    public void contactPurchaseDetail(List<Long> purchaseDetailIdList, Long purchaseId) {
        PurchaseEntity purchaseEntity = null;
        if (purchaseId == null) {
            purchaseEntity = new PurchaseEntity();
            purchaseEntity.setPriority(1);
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            baseMapper.insert(purchaseEntity);
        } else {
            purchaseEntity = baseMapper.selectById(purchaseId);
        }
        List<PurchaseDetailEntity> detailEntityList = purchaseDetailService.listByIds(purchaseDetailIdList);
        this.realContact(detailEntityList, purchaseEntity);
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