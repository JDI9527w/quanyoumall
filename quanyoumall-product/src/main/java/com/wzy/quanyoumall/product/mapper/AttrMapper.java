package com.wzy.quanyoumall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.vo.AttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品属性
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface AttrMapper extends BaseMapper<AttrEntity> {
    Page<AttrVo> queryPage(@Param("page") Page<AttrEntity> page, @Param("attrEntity") AttrEntity attrEntity, @Param("selectType") Integer selectType);
}
