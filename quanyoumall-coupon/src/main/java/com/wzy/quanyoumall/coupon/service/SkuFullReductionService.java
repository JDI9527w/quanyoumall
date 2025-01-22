package com.wzy.quanyoumall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.to.SkuReductionTo;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 21:34:21
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存商品满减信息
     * @param skuReductionTo
     */
    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

