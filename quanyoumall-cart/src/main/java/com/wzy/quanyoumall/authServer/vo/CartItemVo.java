package com.wzy.quanyoumall.authServer.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物项内容
 */
@ToString
@Data
public class CartItemVo {

    private Long skuId;

    private Boolean check = true;

    private String title;

    private String image;

    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    /**
     * 计算当前购物项总价
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = this.price.multiply(new BigDecimal("" + this.count));
    }
}