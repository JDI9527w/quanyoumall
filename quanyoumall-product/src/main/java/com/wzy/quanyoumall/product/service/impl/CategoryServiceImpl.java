package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.exception.bizCodeEnum;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.common.utils.TreeUtil;
import com.wzy.quanyoumall.product.entity.*;
import com.wzy.quanyoumall.product.mapper.CategoryMapper;
import com.wzy.quanyoumall.product.service.*;
import com.wzy.quanyoumall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SpuInfoService spuInfoService;

    @Override
    public List<CategoryEntity> treeSelectCategory() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status", "1"));
        List<CategoryEntity> treeCatetory = TreeUtil.makeTree(categoryEntities,
                category -> category.getCatLevel().equals(1),
                (a, b) -> a.getCatId().equals(b.getParentCid()), CategoryEntity::setChildrenCategory);
        return treeCatetory;
    }

    @Override
    public R removeByIds(List<Long> ids) {
        // 检查有无品牌关联
        List<CategoryBrandRelationEntity> brandRelationList = categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().in("catelog_id", ids));
        // 检查有无属性关联
        List<AttrEntity> attrRelationList = attrService.list(new QueryWrapper<AttrEntity>().in("catelog_id", ids));
        // 检查有无商品关联
        List<SkuInfoEntity> skuRelationList = skuInfoService.list(new QueryWrapper<SkuInfoEntity>().in("catalog_id", ids));
        // 检查有无商品关联
        List<SpuInfoEntity> spuRelationList = spuInfoService.list(new QueryWrapper<SpuInfoEntity>().in("catalog_id", ids));
        if (brandRelationList != null) {
            return R.error(bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getCode(),
                    bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getMsg() + "有品牌与此分类关联,无法删除,请解除关联后再试");
        }
        if (attrRelationList != null) {
            return R.error(bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getCode(),
                    bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getMsg() + "有属性与此分类关联,无法删除,请解除关联后再试");
        }
        if (skuRelationList != null) {
            return R.error(bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getCode(),
                    bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getMsg() + "有商品与此分类关联,无法删除,请解除关联后再试");
        }
        if (spuRelationList != null) {
            return R.error(bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getCode(),
                    bizCodeEnum.PRODUCT_CATALOG_DEL_EXCEPTION.getMsg() + "有商品与此分类关联,无法删除,请解除关联后再试");
        }
        baseMapper.deleteBatchIds(ids);
        return R.ok();
    }

    @Override
    public List<Long> queryLevelCidsBycatelogId(Long catelogId) {
        return baseMapper.queryLevelCidsBycatelogId(catelogId);
    }

    @Override
    public void updateOtherName(CategoryEntity category) {
        categoryBrandRelationService.updateCateNameByCateId(category);
    }

    @Override
    @Transactional
    public void updateAndThen(CategoryEntity category) {
        CategoryEntity temp = baseMapper.selectById(category.getCatId());
        baseMapper.updateById(category);
        if (!temp.getName().equals(category.getName())) {
            this.updateOtherName(category);
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status", 1).eq("cat_level", 1));
    }

    @Cacheable(value = "category", key = "'CatalogJson'")
    @CacheEvict(value = "ca", allEntries = true)
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        List<CategoryEntity> categoryEntities = this.treeSelectCategory();
        Map<String, List<Catelog2Vo>> jsonMap = categoryEntities.stream().collect(Collectors.toMap(item -> item.getCatId().toString(), item -> {
            List<CategoryEntity> lv2List = item.getChildrenCategory();
            List<Catelog2Vo> catelog2VoList = new ArrayList<>();
            if (lv2List != null) {
                catelog2VoList = lv2List.stream().map(lv2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo();
                    catelog2Vo.setCatalog1Id(lv2.getParentCid().toString());
                    catelog2Vo.setId(lv2.getCatId().toString());
                    catelog2Vo.setName(lv2.getName());
                    List<Catelog2Vo.Catelog3Vo> catelog3VoList = lv2.getChildrenCategory().stream().map(lv3 -> {
                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                        catelog3Vo.setCatalog2Id(lv3.getParentCid().toString());
                        catelog3Vo.setId(lv3.getCatId().toString());
                        catelog3Vo.setName(lv3.getName());
                        return catelog3Vo;
                    }).collect(Collectors.toList());
                    catelog2Vo.setCatalog3List(catelog3VoList);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2VoList;
        }));
        return jsonMap;
    }
}