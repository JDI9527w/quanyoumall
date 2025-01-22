/**
 * Copyright 2025 bejson.com
 */
package com.wzy.quanyoumall.product.vo;

import lombok.Data;
import com.wzy.quanyoumall.common.to.MemberPrice;
import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2025-01-21 22:46:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Skus {

    private List<Attr> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<Images> images;
    private List<String> descar;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}