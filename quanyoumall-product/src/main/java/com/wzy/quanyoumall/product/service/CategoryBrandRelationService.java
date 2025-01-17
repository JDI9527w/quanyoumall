package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.BrandEntity;
import com.wzy.quanyoumall.product.entity.CategoryBrandRelationEntity;
import com.wzy.quanyoumall.product.entity.CategoryEntity;

/**
 * 品牌分类关联
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    Page<CategoryBrandRelationEntity> queryPage(Page<CategoryBrandRelationEntity> page, CategoryBrandRelationEntity entity);

    /**
     * 根据品牌id修改关联的冗余字段
     *
     * @param brand
     */
    void updateBrandNameByBrandId(BrandEntity brand);

    /**
     * 根据分类id修改关联的冗余字段
     *
     * @param category
     */
    void updateCateNameByCateId(CategoryEntity category);
}

