package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.BrandEntity;
import com.wzy.quanyoumall.product.entity.CategoryBrandRelationEntity;
import com.wzy.quanyoumall.product.entity.CategoryEntity;
import com.wzy.quanyoumall.product.mapper.CategoryBrandRelationMapper;
import com.wzy.quanyoumall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Override
    public void updateBrandNameByBrandId(BrandEntity brandEntity) {
        CategoryBrandRelationEntity cbre = new CategoryBrandRelationEntity();
        cbre.setBrandName(brandEntity.getName());
        baseMapper.update(cbre, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandEntity.getBrandId()));
    }

    @Override
    public void updateCateNameByCateId(CategoryEntity category) {
        CategoryBrandRelationEntity cbre = new CategoryBrandRelationEntity();
        cbre.setCatelogName(category.getName());
        baseMapper.update(cbre,new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", category.getCatId()));
    }

    @Override
    public List<CategoryBrandRelationEntity> getBrandsListByCatId(Long catId) {
        QueryWrapper<CategoryBrandRelationEntity> qw = new QueryWrapper<>();
        qw.select("id","brand_id","brand_name");
        qw.eq("catelog_id",catId);
        return baseMapper.selectList(qw);
    }

    @Override
    public Page<CategoryBrandRelationEntity> queryPage(Page<CategoryBrandRelationEntity> page, CategoryBrandRelationEntity entity) {
        QueryWrapper<CategoryBrandRelationEntity> qw = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(entity)) {
            if (StringUtils.isNotEmpty(String.valueOf(entity.getBrandId()))) {
                qw.eq("brand_id", entity.getBrandId());
            }
        }
        return baseMapper.selectPage(page, qw);
    }
}