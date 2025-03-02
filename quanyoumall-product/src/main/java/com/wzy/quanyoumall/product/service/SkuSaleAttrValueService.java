package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.SkuSaleAttrValueEntity;
import com.wzy.quanyoumall.product.vo.SkuItemSaleAttrVo;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> listGetsaleAttrsBySpuId(Long spuId);
}

