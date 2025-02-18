package com.wzy.quanyoumall.product.vo;

import com.wzy.quanyoumall.product.entity.SkuImagesEntity;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;
import com.wzy.quanyoumall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;


@Data
public class SkuItemVo {

    //1、sku基本信息获取 pms_sku_info
    private SkuInfoEntity info;
    private boolean hasStock = true;//库存
    //2、sku图片信息    pms_sku_images
    private List<SkuImagesEntity> images;
    //3、获取spu的销售属性组合
    private List<SkuItemSaleAttrVo> saleAttr;
    //4、获取spu的介绍
    private SpuInfoDescEntity desp;
    //5、获取spu的规格参数信息
    private List<SpuItemAttrGroupVo> groupAttrs;

    //6、秒杀商品的优惠信息
    private SeckillSkuVo seckillSkuVo;


}
