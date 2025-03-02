package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.utils.PageUtils;

import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;
import com.wzy.quanyoumall.product.mapper.AttrAttrgroupRelationMapper;
import com.wzy.quanyoumall.product.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    

    @Override
    public void deleteByAttrIds(List<Long> attrIds) {
        baseMapper.deleteByAttrIds(attrIds);
    }

    @Override
    public void deleteByAttrIdAndGroupId(List<AttrAttrgroupRelationEntity> relationEntities) {
        baseMapper.deleteByAttrIdAndGroupId(relationEntities);
    }
}