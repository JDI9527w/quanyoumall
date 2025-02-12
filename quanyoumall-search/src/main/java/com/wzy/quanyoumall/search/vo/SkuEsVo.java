package com.wzy.quanyoumall.search.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuEsVo {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal price;
    private String skuDefaultImg;
    private Long saleCount;
    // 是否有库存
    private Boolean hasStock;
    // 热度
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    // 品牌名
    private String brandName;
    // 品牌图片url
    private String brandImg;
    // 商品分类名称
    private String catalogName;
    private List<Attrs> attrs;

    @Data
    public static class Attrs {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }

}
