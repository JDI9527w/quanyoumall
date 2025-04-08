package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.SkuImagesEntity;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;
import com.wzy.quanyoumall.product.entity.SpuInfoDescEntity;
import com.wzy.quanyoumall.product.mapper.SkuInfoMapper;
import com.wzy.quanyoumall.product.service.*;
import com.wzy.quanyoumall.product.vo.SkuCheckVo;
import com.wzy.quanyoumall.product.vo.SkuItemSaleAttrVo;
import com.wzy.quanyoumall.product.vo.SkuItemVo;
import com.wzy.quanyoumall.product.vo.SpuItemAttrGroupVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Page<SkuInfoEntity> queryPage(SkuCheckVo skuCheckVo, Page<SkuInfoEntity> page) {
        QueryWrapper<SkuInfoEntity> qw = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(skuCheckVo)) {
            if (skuCheckVo.getCatalogId() != null) {
                qw.eq("catalog_id", skuCheckVo.getCatalogId());
            }
            if (skuCheckVo.getBrandId() != null) {
                qw.eq("brand_id", skuCheckVo.getBrandId());
            }
            if (skuCheckVo.getMinPrice() != null && skuCheckVo.getMinPrice().compareTo(new BigDecimal(0)) > 0) {
                qw.ge("price", skuCheckVo.getMinPrice());
            }
            if (skuCheckVo.getMaxPrice() != null && skuCheckVo.getMaxPrice().compareTo(new BigDecimal(0L)) > 0) {
                qw.le("price", skuCheckVo.getMaxPrice());
            }
            if (StringUtils.isNotBlank(skuCheckVo.getKey())) {
                qw.and(q -> {
                    q.like("sku_name", skuCheckVo.getKey()).or();
                    q.like("sku_desc", skuCheckVo.getKey()).or();
                    q.like("sku_title", skuCheckVo.getKey()).or();
                    q.like("sku_subtitle", skuCheckVo.getKey());
                });
            }
        }
        return baseMapper.selectPage(page, qw);
    }

    @Override
    public List<SkuInfoEntity> listSkuInfoBySpuId(Long spuId) {
        return baseMapper.selectList(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    @Override
    public SkuItemVo getSkuDetailById(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            //1、sku基本信息获取pms_sku_info
            SkuInfoEntity skuInfoEntity = baseMapper.selectById(skuId);
            skuItemVo.setInfo(skuInfoEntity);
            return skuInfoEntity;
        }, threadPoolExecutor);
        CompletableFuture<Void> skuImgFuture = CompletableFuture.runAsync(() -> {
            //2、sku的图片信息、pms_sku_images
            List<SkuImagesEntity> skuImagesList = skuImagesService.list(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
            skuItemVo.setImages(skuImagesList);
        }, threadPoolExecutor);

        CompletableFuture<Void> skuItemSaleAttrFuture = skuInfoFuture.thenAcceptAsync((res) -> {
            //3、获取spu的销售属性组合。
            List<SkuItemSaleAttrVo> saleAttrs = skuSaleAttrValueService.listGetsaleAttrsBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrs);
        }, threadPoolExecutor);

        CompletableFuture<Void> spuInfoDescFuture = skuInfoFuture.thenAcceptAsync((res) -> {
            //4、获取spu的介绍
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDescp(spuInfoDesc);
        }, threadPoolExecutor);

        CompletableFuture<Void> spuItemAttrGroupFuture = skuInfoFuture.thenAcceptAsync((res) -> {
            //5、获取spu的规格参数信息。
            List<SpuItemAttrGroupVo> spuItemAttrGroupVos = attrGroupService.listGetAttrGroupAndAttrBySpuId(res.getSpuId(), res.getCatalogId());
            skuItemVo.setGroupAttrs(spuItemAttrGroupVos);
        }, threadPoolExecutor);

        CompletableFuture.allOf(skuImgFuture, skuItemSaleAttrFuture, spuInfoDescFuture, spuItemAttrGroupFuture).get();
        return skuItemVo;
    }

    @Override
    public List<SkuInfoEntity> queryListByIds(List<Long> skuIds) {
        if (ObjectUtils.isNotEmpty(skuIds)) {
            return baseMapper.selectList(new QueryWrapper<SkuInfoEntity>().in("sku_id", skuIds));
        }
        return null;
    }
}