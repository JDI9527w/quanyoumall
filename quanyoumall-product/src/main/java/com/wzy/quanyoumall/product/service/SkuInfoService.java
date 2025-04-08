package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;
import com.wzy.quanyoumall.product.vo.SkuCheckVo;
import com.wzy.quanyoumall.product.vo.SkuItemVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    /**
     * 通过skuId查询商品详情
     *
     * @param skuId
     * @return
     */
    SkuItemVo getSkuDetailById(Long skuId) throws ExecutionException, InterruptedException;

    /**
     * 通过skuIds查询商品详情集合
     *
     * @param skuIds
     * @return
     */
    List<SkuInfoEntity> queryListByIds(List<Long> skuIds);
}

