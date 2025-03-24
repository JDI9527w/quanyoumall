package com.wzy.quanyoumall.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WareSkuLockVo {
    private String OrderSn;

    private List<OrderItemVo> locks;
}