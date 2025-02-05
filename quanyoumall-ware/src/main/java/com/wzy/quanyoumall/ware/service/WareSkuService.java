package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;

/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    Page<WareSkuEntity> queryPage(WareSkuEntity wareSkuEntity, Page<WareSkuEntity> page);
}

