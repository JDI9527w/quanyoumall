package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;
import com.wzy.quanyoumall.product.vo.SkuCheckVo;

import java.util.List;

/**
 * sku信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    Page<SkuInfoEntity> queryPage(SkuCheckVo skuCheckVo, Page<SkuInfoEntity> page);

    /**
     * 通过spuId查询关联的SkuInfo对象.
     *
     * @param spuId
     * @return
     */
    List<SkuInfoEntity> listSkuInfoBySpuId(Long spuId);
}

