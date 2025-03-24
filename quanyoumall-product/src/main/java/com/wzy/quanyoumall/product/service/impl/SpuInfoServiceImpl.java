package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.constant.ProductConstant;
import com.wzy.quanyoumall.common.to.SkuReductionTo;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.common.to.SpuBoundsTo;
import com.wzy.quanyoumall.common.utils.ObjectBeanUtils;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.*;
import com.wzy.quanyoumall.product.feign.CouponFeignService;
import com.wzy.quanyoumall.product.feign.SearchFeignService;
import com.wzy.quanyoumall.product.feign.WareFeignService;
import com.wzy.quanyoumall.product.mapper.SpuInfoMapper;
import com.wzy.quanyoumall.product.service.*;
import com.wzy.quanyoumall.product.vo.Bounds;
import com.wzy.quanyoumall.product.vo.Images;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;
import com.wzy.quanyoumall.product.vo.es.SkuEsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
    @Qualifier("com.wzy.quanyoumall.product.feign.WareFeignService")
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SearchFeignService searchFeignService;

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


    @Override
    public void upSpuById(Long spuId) {
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.listSkuInfoBySpuId(spuId);
        List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        // 查询库存
        Map<Long, Boolean> stockMap = checkSkuStock(skuIds);
        // 查询可供检索的属性
        List<ProductAttrValueEntity> attrSpuList = productAttrValueService.listGetNeedSearchAttrBySpuId(spuId);
        List<SkuEsVo.Attrs> attrsList = ObjectBeanUtils.cpProperties(attrSpuList, SkuEsVo.Attrs.class);
        // 获取分类对象
        SpuInfoEntity spuInfoEntity = baseMapper.selectById(spuId);
        CategoryEntity categoryEntity = categoryService.getById(spuInfoEntity.getCatalogId());
        String categoryName = categoryEntity.getName();
        // 获取品牌对象
        BrandEntity brandEntity = brandService.getById(spuInfoEntity.getBrandId());
        String brandName = brandEntity.getName();
        String brandLogo = brandEntity.getLogo();
        // 赋值
        List<SkuEsVo> skuEsVoList = ObjectBeanUtils.cpProperties(skuInfoEntities, SkuEsVo.class);
        skuEsVoList.forEach(item -> {
            Boolean hasStock = null;
            if (stockMap != null) {
                hasStock = stockMap.get(item.getSkuId());
            }
            item.setHasStock(hasStock != null ? hasStock : false);
            item.setHotScore(0L);
            item.setBrandName(brandName);
            item.setBrandImg(brandLogo);
            item.setCatalogName(categoryName);
            item.setAttrs(attrsList);
        });
        //  幂等性问题,后期可以加入消息队列,或者新建表,如果商品上架成功,将商品id插入表中,上架前,先查询表,没有再上.有就更新?
        //  测试不会发生，elasticsearch的bulk如果包含id会执行更新操作，不会插入新值。搁置。
        R productUpRes = searchFeignService.productUp(skuEsVoList);
        if (productUpRes.getCode() == 0) {
            spuInfoEntity.setPublishStatus(ProductConstant.StatusEnum.UP_STATE.getCode());
            baseMapper.updateById(spuInfoEntity);
        } else {
            log.error(productUpRes.toString());
        }
    }

    /**
     * 查询库存
     *
     * @param skuIds
     * @return
     */
    private Map<Long, Boolean> checkSkuStock(List<Long> skuIds) {
        Map<Long, Boolean> stockMap = null;
        try {
            R resultMap = wareFeignService.infoBySkuId(skuIds);
            List<SkuStockTO> skuStockTOS = ObjectBeanUtils.extractAndDeserializeListFromJson(resultMap, "data", SkuStockTO.class);
            if (skuStockTOS != null) {
                stockMap = skuStockTOS.stream().collect(Collectors.toMap(SkuStockTO::getSkuId, SkuStockTO::getHasStock));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockMap;
    }

    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity sku = skuInfoService.getById(skuId);
        SpuInfoEntity spu = this.getById(sku.getSpuId());
        return spu;
    }
}