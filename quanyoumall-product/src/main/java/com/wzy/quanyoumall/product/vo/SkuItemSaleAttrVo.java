package com.wzy.quanyoumall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private String attrValues;
//    private List<AttrValueWithSkuIdVo> attrValues;
}
