package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.SpuInfoEntity;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;

/**
 * spu信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    Page<SpuInfoEntity> queryPage(SpuInfoEntity spuInfoEntity, Page<SpuInfoEntity> page);

    void saveBySpuSaveVo(SpuSaveVo spuSaveVo);

    /**
     * 上架商品
     *
     * @param spuId 商品id
     */
    void upSpuById(Long spuId);

    /**
     * 通过skuid获取Spu
     *
     * @param skuId
     * @return
     */
    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

