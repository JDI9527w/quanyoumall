package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskEntity;

/**
 * 库存工作单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    Page<WareOrderTaskEntity> queryPage(WareOrderTaskEntity wareOrderTaskEntity, Page<WareOrderTaskEntity> page);

    /**
     * 通过订单号获取库存工作单
     *
     * @param orderSn
     * @return
     */
    WareOrderTaskEntity getTaskByOrderSn(String orderSn);
}

