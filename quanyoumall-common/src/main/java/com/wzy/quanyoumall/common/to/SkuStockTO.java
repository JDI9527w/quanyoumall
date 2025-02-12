package com.wzy.quanyoumall.common.to;

import lombok.Data;

@Data
public class SkuStockTO {
    private Long skuId;
    private Integer stockSum;
    private Boolean hasStock;
}
