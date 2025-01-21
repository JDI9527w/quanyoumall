package com.wzy.quanyoumall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface AttrGroupMapper extends BaseMapper<AttrGroupEntity> {

    List<AttrEntity> getRelationByAttrGroupId(@Param("attrGroupId") String attrGroupId);

    Page<AttrEntity> selectAllNoAttrByGroupId(@Param("page") Page<AttrEntity> page,
                                              @Param("attrGroupId") String attrGroupId,
                                              @Param("paramName") String paramName,
                                              @Param("attrType") int attrType);
}
