package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.to.SkuReductionTo;
import com.wzy.quanyoumall.common.to.SpuBoundsTo;
import com.wzy.quanyoumall.product.entity.*;
import com.wzy.quanyoumall.product.feign.CouponFeignService;
import com.wzy.quanyoumall.product.mapper.SpuInfoMapper;
import com.wzy.quanyoumall.product.service.*;
import com.wzy.quanyoumall.product.vo.Bounds;
import com.wzy.quanyoumall.product.vo.Images;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;

    @Transactional
    @Override
    public void saveBySpuSaveVo(SpuSaveVo spuSaveVo) {
//      保存spu信息 pms_spu_info
        SpuInfoEntity spu = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spu);
        baseMapper.insert(spu);

//      保存spu描述图片 pms_spu_info_desc
        SpuInfoDescEntity side = new SpuInfoDescEntity();
        side.setSpuId(spu.getId());
        side.setDecript(String.join(",", spuSaveVo.getDecript()));
        spuInfoDescService.save(side);
//      保存spu图片集 pms_spu_images
        spuImagesService.saveImgBatch(spu.getId(), spuSaveVo.getImages());
//      保存spu规格参数 pms_product_attr_value
        productAttrValueService.saveAttrBatch(spu.getId(), spuSaveVo.getBaseAttrs());
//      保存spu积分信息 sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        spuBoundsTo.setSpuId(spu.getId());
        couponFeignService.saveSpuBounds(spuBoundsTo);
//      保存spu对应的sku
//      sku 基本信息 pms_sku_info
        spuSaveVo.getSkus().forEach(item -> {
            List<Images> imagesList = item.getImages();
            String defaultImg = "";
            for (Images img : imagesList) {
                if (img.getDefaultImg() == 1) {
                    defaultImg = img.getImgUrl();
                }
            }
            SkuInfoEntity sku = new SkuInfoEntity();
            BeanUtils.copyProperties(item, sku);
            sku.setBrandId(spu.getBrandId());
            sku.setCatalogId(spu.getCatalogId());
            sku.setSpuId(spu.getId());
            sku.setSkuDefaultImg(defaultImg);
            sku.setSaleCount(0L);
            skuInfoService.save(sku);

//          sku 图片信息 pms_sku_images
            List<SkuImagesEntity> skuImgList = imagesList.stream().map(img -> {
                SkuImagesEntity skuImg = new SkuImagesEntity();
                BeanUtils.copyProperties(img, skuImg);
                skuImg.setSkuId(sku.getSkuId());
                return skuImg;
            }).filter(skuImg -> StringUtils.isNotEmpty(skuImg.getImgUrl()))
                    .collect(Collectors.toList());
            skuImagesService.saveBatch(skuImgList);

//          sku 销售属性 pms_sku_sale_attr_value
            List<SkuSaleAttrValueEntity> skuAttrList = item.getAttr().stream().map(attr -> {
                SkuSaleAttrValueEntity skuAttr = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(attr, skuAttr);
                skuAttr.setSkuId(sku.getSkuId());
                return skuAttr;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(skuAttrList);
//         sku 优惠/满减信息. 优惠 sms_sku_loadder / 满减 sms_sku_full_reduction
            SkuReductionTo srto = new SkuReductionTo();
            BeanUtils.copyProperties(item, srto);
            if (srto.getFullCount() > 0 || srto.getFullPrice().compareTo(new BigDecimal("0")) == 1)
                couponFeignService.saveSkuReductionTo(srto);
        });
    }

    @Override
    public Page<SpuInfoEntity> queryPage(SpuInfoEntity spuInfoEntity, Page<SpuInfoEntity> page) {
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(spuInfoEntity)) {
            if (spuInfoEntity.getBrandId() != null) {
                queryWrapper.eq("brand_id", spuInfoEntity.getBrandId());
            }
            if (spuInfoEntity.getCatalogId() != null && spuInfoEntity.getCatalogId() != 0L) {
                queryWrapper.eq("catalog_id", spuInfoEntity.getCatalogId());
            }
            if (spuInfoEntity.getPublishStatus() != null) {
                queryWrapper.eq("publish_status", spuInfoEntity.getPublishStatus());
            }
            if (StringUtils.isNotBlank(spuInfoEntity.getSpuDescription())) {
                queryWrapper.and((qw) -> {
                    qw.like("spu_name", spuInfoEntity.getSpuDescription())
                            .or()
                            .like("spu_description", spuInfoEntity.getSpuDescription());
                });
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }
}