package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.vo.AttrVo;

import java.util.List;

/**
 * 商品属性
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface AttrService extends IService<AttrEntity> {

    /**
     * 分页查询
     *
     * @param page
     * @param attrEntity
     * @param selectType
     * @return
     */
    Page<AttrVo> queryPage(Page<AttrEntity> page, AttrEntity attrEntity, Integer selectType);

    /**
     * 保存及相关业务逻辑
     *
     * @param attrVo
     */
    void saveAndThen(AttrVo attrVo);

    /**
     * 删除及相关业务逻辑
     *
     * @param attrIds
     */
    void removeByIdsAndThen(List<Long> attrIds);

    /**
     * 通过 attrId查询详情
     *
     * @param attrId 属性id
     * @return
     */
    AttrVo getDetailById(Long attrId);

    /**
     * 更新及相关业务逻辑
     *
     * @param attrVo 属性vo类
     */
    void updateByIdAndThen(AttrVo attrVo);

    /**
     * 通过分类id查询销售属性.
     *
     * @param catelogId 分类id
     * @return
     */
    List<AttrEntity> getSaleListByCatelogId(Long catelogId);
}

