package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;

import java.util.List;

/**
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    /**
     * 分页查询
     *
     * @param purchaseDetailEntity
     * @param page
     * @return
     */
    Page<PurchaseDetailEntity> queryPage(PurchaseDetailEntity purchaseDetailEntity, Page<PurchaseDetailEntity> page);

    /**
     * 通过关联的采购单id和传递的状态值修改状态.
     *
     * @param realPurchaseIds 采购单id
     * @param statusCode      状态值
     */
    void changeStatusByPurchaseIds(List<Long> realPurchaseIds, Integer statusCode);

    /**
     * 通过采购单id查询关联的采购项
     *
     * @param purchaseId
     * @return
     */
    List<PurchaseDetailEntity> listByPurchaseId(Long purchaseId);
}

