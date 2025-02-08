package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;
import com.wzy.quanyoumall.ware.mapper.PurchaseDetailMapper;
import com.wzy.quanyoumall.ware.service.PurchaseDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public Page<PurchaseDetailEntity> queryPage(PurchaseDetailEntity purchaseDetailEntity, Page<PurchaseDetailEntity> page) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(purchaseDetailEntity)) {
            if (purchaseDetailEntity.getWareId() != null) {
                queryWrapper.eq("ware_id", purchaseDetailEntity.getWareId());
            }
            if (purchaseDetailEntity.getStatus() != null) {
                queryWrapper.eq("status", purchaseDetailEntity.getStatus());
            }
            if (purchaseDetailEntity.getPurchaseId() != null) {
                queryWrapper.eq("purchase_id", purchaseDetailEntity.getPurchaseId());
            }
            if (purchaseDetailEntity.getSkuId() != null) {
                queryWrapper.eq("sku_id", purchaseDetailEntity.getSkuId());
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void changeStatusByPurchaseIds(List<Long> realPurchaseIds, Integer statusCode) {
        realPurchaseIds.forEach(purId -> {
            List<PurchaseDetailEntity> purchaseDetailEntities = this.listByPurchaseId(purId);
            purchaseDetailEntities.forEach(item -> {
                item.setStatus(statusCode);
            });
            this.updateBatchById(purchaseDetailEntities);
        });
    }

    @Override
    public List<PurchaseDetailEntity> listByPurchaseId(Long purId) {
        return baseMapper.selectList(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", purId));
    }
}