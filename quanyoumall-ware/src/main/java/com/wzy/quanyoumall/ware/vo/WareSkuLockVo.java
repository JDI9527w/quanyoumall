package com.wzy.quanyoumall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {
    private String OrderSn;

    private List<OrderItemVo> locks;
}