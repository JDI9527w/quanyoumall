package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;
import com.wzy.quanyoumall.product.mapper.SkuInfoMapper;
import com.wzy.quanyoumall.product.service.SkuInfoService;
import com.wzy.quanyoumall.product.vo.SkuCheckVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfoEntity> implements SkuInfoService {

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

}