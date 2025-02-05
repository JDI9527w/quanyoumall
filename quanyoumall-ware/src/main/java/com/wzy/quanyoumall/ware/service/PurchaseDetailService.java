package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;

/**
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    Page<PurchaseDetailEntity> queryPage(PurchaseDetailEntity purchaseDetailEntity, Page<PurchaseDetailEntity> page);
}

