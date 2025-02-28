package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import com.wzy.quanyoumall.product.vo.AttrGroupVo;
import com.wzy.quanyoumall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Set;

/**
 * 属性分组
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    Page<AttrGroupEntity> queryPage(Page<AttrGroupEntity> page, AttrGroupEntity attrGroupEntity);

    /**
     * 通过属性组id获取关联的属性列表
     *
     * @param attrGroupId
     * @return
     */
    List<AttrEntity> getRelationByAttrGroupId(String attrGroupId);

    /**
     * 通过分组id 和 参数查询 关联商品属性分页列表
     *
     * @param page        分页条件
     * @param attrGroupId
     * @param paramName   查询条件
     * @param attrType    查询类型.
     * @return
     */
    Page<AttrEntity> selectAllNoAttrByGroupId(Page<AttrEntity> page,
                                              String attrGroupId,
                                              String paramName,
                                              int attrType);

    /**
     * 通过分类id查询相关分组以及分组的属性.
     *
     * @param catId
     * @return
     */
    List<AttrGroupVo> getAttrListByCatId(Long catId);

    Set<Long> removeByGroupIds(List<Long> attrGroupIds);

    List<SpuItemAttrGroupVo> listGetAttrGroupAndAttrBySpuId(Long spuId, Long catalogId);
}

