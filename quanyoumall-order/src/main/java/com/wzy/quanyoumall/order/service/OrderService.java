package com.wzy.quanyoumall.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import com.wzy.quanyoumall.order.vo.*;

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

    /**
     * 获取支付宝支付对象
     *
     * @param orderSn
     * @return
     */
    PayVo aliPayOrder(String orderSn);

    /**
     * 订单列表分页查询
     *
     * @param objectPage
     * @param queryParam
     * @return
     */
    Page<OrderEntity> pageByQueryParam(Page<OrderEntity> objectPage, String queryParam);

    /**
     * 修改订单状态
     *
     * @param orderSn
     * @param statusCode
     * @param payType
     * @return
     */
    void changeStatusBySn(String orderSn, Integer statusCode, Integer payType);

    /**
     * 处理支付结果
     *
     * @param payAsyncVo
     * @return
     */
    String handlerPayResult(PayAsyncVo payAsyncVo);
}

