package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.CategoryEntity;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface CategoryService extends IService<CategoryEntity> {

    R treeSelectCategory();

    R removeByIds(List<Long> ids);

    /**
     * 递归查询当前层级的父级结构.
     *
     * @param catelogId
     * @return
     */
    List<Long> queryLevelCidsBycatelogId(Long catelogId);

    void updateOtherName(CategoryEntity category);

    /**
     * 更新及之后要做的一些事
     * @param category
     */
    void updateAndThen(CategoryEntity category);
}

