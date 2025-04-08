package com.wzy.quanyoumall.authServer.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillSkuRedisTo {
    private Long id;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
    /**
     * sku详细信息
     */
    private SeckillSkuInfoTo skuInfoTo;
    /**
     * 活动开始时间
     */
    private Long startTime;
    /**
     * 活动结束时间
     */
    private Long endTime;
    /**
     * 随机码
     */
    private String randomCode;
}
