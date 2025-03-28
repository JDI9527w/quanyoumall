package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    void deleteByAttrIds(List<Long> attrIds);

    void deleteByAttrIdAndGroupId(List<AttrAttrgroupRelationEntity> relationEntities);
}

