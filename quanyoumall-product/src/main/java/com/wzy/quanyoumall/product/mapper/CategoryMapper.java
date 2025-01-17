package com.wzy.quanyoumall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

    List<Long> queryLevelCidsBycatelogId(@Param("catelogId") Long catelogId);
}
