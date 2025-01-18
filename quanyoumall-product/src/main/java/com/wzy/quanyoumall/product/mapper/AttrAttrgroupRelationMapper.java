package com.wzy.quanyoumall.product.mapper;

import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteByAttrIds(@Param("attrIds") List<Long> attrIds);
}
