package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

