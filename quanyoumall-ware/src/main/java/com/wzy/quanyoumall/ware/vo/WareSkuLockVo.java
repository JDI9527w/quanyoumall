package com.wzy.quanyoumall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {
    private Long orderId;

    private String orderSn;

    private List<OrderItemVo> locks;
}