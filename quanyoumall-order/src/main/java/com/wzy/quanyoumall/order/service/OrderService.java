package com.wzy.quanyoumall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import com.wzy.quanyoumall.order.vo.OrderConfirmVo;
import com.wzy.quanyoumall.order.vo.OrderSubmitVo;
import com.wzy.quanyoumall.order.vo.SubmitOrderRespVo;

/**
 * 订单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
public interface OrderService extends IService<OrderEntity> {
    /**
     * 确认订单
     *
     * @return
     */
    OrderConfirmVo confirmOrder();

    /**
     * 提交订单
     *
     * @param submitVo 订单提交信息
     * @return
     */
    SubmitOrderRespVo submitOrder(OrderSubmitVo submitVo);

    /**
     * 修改订单状态
     *
     * @param orderEntity
     */
    void closeOrder(OrderEntity orderEntity);

    /**
     * 通过orderSN获取订单
     *
     * @param orderSn
     * @return
     */
    OrderEntity getByOrderSn(String orderSn);
}

