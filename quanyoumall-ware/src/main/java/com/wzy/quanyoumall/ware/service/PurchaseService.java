package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.Dto.PurchaseFinshDTO;
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

    /**
     * 分页查询
     *
     * @param purchaseEntity
     * @param page
     * @return
     */
    Page<PurchaseEntity> queryPage(PurchaseEntity purchaseEntity, Page<PurchaseEntity> page);

    /**
     * 条件查询
     *
     * @param checkStatus
     * @return
     */
    List<PurchaseEntity> queryListByCondition(String checkStatus);

    /**
     * 关联采购单与采购项
     *
     * @param purchaseDetailIdList
     * @param purchaseId
     * @return
     */
    R contactPurchaseDetail(List<Long> purchaseDetailIdList, Long purchaseId);

    /**
     * 领取采购单
     *
     * @param purchaseIds
     */
    void receivePurchaseByPurchaseIds(List<Long> purchaseIds);

    /**
     * 完成采购单
     *
     * @param purchaseFinshDTO
     */
    void finshPurchase(PurchaseFinshDTO purchaseFinshDTO);
}

