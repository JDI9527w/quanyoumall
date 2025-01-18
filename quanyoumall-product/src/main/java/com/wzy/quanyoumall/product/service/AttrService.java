package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.BrandEntity;
import com.wzy.quanyoumall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 18:48:48
 */
public interface AttrService extends IService<AttrEntity> {

    Page<AttrVo> queryPage(Page<AttrEntity> page, AttrEntity attrEntity,Integer selectType);

    void saveAndThen(AttrVo attrVo);

    void removeByIdsAndThen(List<Long> attrIds);

    AttrVo getDetailById(Long attrId);

    void updateByIdAndThen(AttrVo attrVo);
}

