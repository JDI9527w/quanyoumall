package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

