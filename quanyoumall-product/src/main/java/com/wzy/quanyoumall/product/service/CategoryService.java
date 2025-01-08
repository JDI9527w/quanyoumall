package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R treeSelectCategory();

    R removeByIds(List<Long> ids);
}

