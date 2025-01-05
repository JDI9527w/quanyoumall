package com.wzy.quanyoumall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.common.utils.Query;

import com.wzy.quanyoumall.product.mapper.CategoryBrandRelationMapper;
import com.wzy.quanyoumall.product.entity.CategoryBrandRelationEntity;
import com.wzy.quanyoumall.product.service.CategoryBrandRelationService;


@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

}