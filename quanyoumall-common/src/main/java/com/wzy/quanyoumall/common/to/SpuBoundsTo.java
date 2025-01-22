package com.wzy.quanyoumall.common.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuBoundsTo {
    /**
     * spuID
     */
    private Long spuId;
    /**
     * 成长积分
     */
    private BigDecimal growBounds;
    /**
     * 购物积分
     */
    private BigDecimal buyBounds;
}
