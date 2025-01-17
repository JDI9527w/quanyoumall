package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.BrandEntity;
import com.wzy.quanyoumall.product.mapper.BrandMapper;
import com.wzy.quanyoumall.product.service.BrandService;
import com.wzy.quanyoumall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public Page<BrandEntity> queryPage(Page<BrandEntity> page, BrandEntity brandEntity) {
        QueryWrapper<BrandEntity> qw = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(brandEntity)) {
            if (StringUtils.isNotEmpty(brandEntity.getName())) {
                qw.like("name", brandEntity.getName());
                qw.or().like("descript", brandEntity.getName());
            }
        }
        Page<BrandEntity> result = baseMapper.selectPage(page, qw);
        return result;
    }

    @Override
    public void updateOtherName(BrandEntity brand) {
        categoryBrandRelationService.updateBrandNameByBrandId(brand);
    }

    @Override
    @Transactional
    public void updateAndThen(BrandEntity brand) {
        BrandEntity temp = baseMapper.selectById(brand.getBrandId());
        baseMapper.updateById(brand);
        if (!temp.getName().equals(brand.getName())) {
            this.updateOtherName(brand);
        }
    }
}