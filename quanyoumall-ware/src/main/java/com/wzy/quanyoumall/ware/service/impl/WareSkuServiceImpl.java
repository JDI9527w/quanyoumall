package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import com.wzy.quanyoumall.ware.mapper.WareSkuMapper;
import com.wzy.quanyoumall.ware.service.WareSkuService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSkuEntity> implements WareSkuService {

    @Override
    public Page<WareSkuEntity> queryPage(WareSkuEntity wareSkuEntity, Page<WareSkuEntity> page) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(wareSkuEntity)) {
            if (wareSkuEntity.getWareId() != null) {
                queryWrapper.eq("ware_id", wareSkuEntity.getWareId());
            }
            if (wareSkuEntity.getSkuId() != null) {
                queryWrapper.eq("sku_id", wareSkuEntity.getSkuId());
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }
}