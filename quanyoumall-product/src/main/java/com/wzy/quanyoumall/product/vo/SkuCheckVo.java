package com.wzy.quanyoumall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuCheckVo {

    /**
     * 查询关键字
     */
    private String key;
    /**
     * 所属分类id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 最高价格
     */
    private BigDecimal maxPrice;
    /**
     * 最低价格
     */
    private BigDecimal minPrice;
}
