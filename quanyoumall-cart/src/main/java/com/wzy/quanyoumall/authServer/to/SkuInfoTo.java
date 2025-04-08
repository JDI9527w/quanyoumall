package com.wzy.quanyoumall.authServer.to;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Data
public class SkuInfoTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * skuId
     */
    private Long skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * sku介绍描述
     */
    private String skuDesc;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 默认图片
     */
    private String skuDefaultImg;
    /**
     * 标题
     */
    private String skuTitle;
    /**
     * 副标题
     */
    private String skuSubtitle;
    /**
     * 价格
     */
    private BigDecimal price;
}
