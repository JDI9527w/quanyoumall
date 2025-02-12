package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;

import java.util.List;

/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    Page<WareSkuEntity> queryPage(WareSkuEntity wareSkuEntity, Page<WareSkuEntity> page);

    /**
     * 根据skuIds查询对应商品总库存
     *
     * @param skuIds
     * @return
     */
    List<SkuStockTO> getStokeBySkuIds(List<Long> skuIds);

    /**
     * 根据skuIds查询对应商品是否有库存
     *
     * @param skuIds
     * @return
     */
    List<SkuStockTO> getHasStokeBySkuIds(List<Long> skuIds);
}

