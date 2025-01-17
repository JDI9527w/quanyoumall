package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import com.wzy.quanyoumall.product.mapper.AttrGroupMapper;
import com.wzy.quanyoumall.product.service.AttrGroupService;
import org.springframework.stereotype.Service;


@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {

    @Override
    public Page<AttrGroupEntity> queryPage(Page<AttrGroupEntity> page, AttrGroupEntity attrGroupEntity) {
        QueryWrapper<AttrGroupEntity> qw = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(attrGroupEntity)) {
            if (StringUtils.isNotEmpty(attrGroupEntity.getAttrGroupName())) {
                qw.like("attr_group_name", attrGroupEntity.getAttrGroupName());
                qw.or();
                qw.like("descript", attrGroupEntity.getDescript());
            }
            if (attrGroupEntity.getCatelogId() != null) {
                qw.eq("catelog_id", attrGroupEntity.getCatelogId());
            }
        }
        return baseMapper.selectPage(page, qw);
    }
}