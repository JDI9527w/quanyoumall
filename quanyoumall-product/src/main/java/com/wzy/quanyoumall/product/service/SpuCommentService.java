package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

