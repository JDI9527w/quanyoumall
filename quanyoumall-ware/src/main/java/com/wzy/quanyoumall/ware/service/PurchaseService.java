package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.PurchaseEntity;

import java.util.List;

/**
 * 采购信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    Page<PurchaseEntity> queryPage(PurchaseEntity purchaseEntity, Page<PurchaseEntity> page);

    List<PurchaseEntity> queryListByCondition(String checkStatus);

    void contactPurchaseDetail(List<Long> purchaseDetailIdList, Long purchaseId);
}

