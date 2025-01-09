package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.common.utils.Query;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.common.utils.TreeUtil;
import com.wzy.quanyoumall.product.entity.CategoryEntity;
import com.wzy.quanyoumall.product.mapper.CategoryMapper;
import com.wzy.quanyoumall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Override
    public R treeSelectCategory() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status","1"));
        List<CategoryEntity> treeCatetory = TreeUtil.makeTree(categoryEntities,
                category -> category.getCatLevel().equals(1),
                (a, b) -> a.getCatId().equals(b.getParentCid()), CategoryEntity::setChildrenCategory);
        return R.ok().put("data",treeCatetory);
    }

    @Override
    public R removeByIds(List<Long> ids) {
        // TODO:check有无商品关联.
        int i = baseMapper.deleteBatchIds(ids);
        return R.ok();
    }
}