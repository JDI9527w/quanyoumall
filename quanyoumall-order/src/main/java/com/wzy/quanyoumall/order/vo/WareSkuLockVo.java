package com.wzy.quanyoumall.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WareSkuLockVo {
    private Long orderId;

    private String orderSn;

    private List<OrderItemVo> locks;
}