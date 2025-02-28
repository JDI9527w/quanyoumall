package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import com.wzy.quanyoumall.product.mapper.AttrGroupMapper;
import com.wzy.quanyoumall.product.service.AttrAttrgroupRelationService;
import com.wzy.quanyoumall.product.service.AttrGroupService;
import com.wzy.quanyoumall.product.service.AttrService;
import com.wzy.quanyoumall.product.vo.AttrGroupVo;
import com.wzy.quanyoumall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    private AttrService attrService;

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

    @Override
    public List<AttrEntity> getRelationByAttrGroupId(String attrGroupId) {
        return baseMapper.getRelationByAttrGroupId(attrGroupId);
    }

    @Override
    public Page<AttrEntity> selectAllNoAttrByGroupId(Page<AttrEntity> page, String attrGroupId, String paramName, int attrType) {
        return baseMapper.selectAllNoAttrByGroupId(page, attrGroupId, paramName, attrType);
    }

    @Override
    public List<AttrGroupVo> getAttrListByCatId(Long catId) {
        List<AttrGroupEntity> groupList = baseMapper.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        return groupList.stream().map(obj -> {
            AttrGroupVo agv = new AttrGroupVo();
            BeanUtils.copyProperties(obj, agv);
            List<AttrAttrgroupRelationEntity> relationList = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", agv.getAttrGroupId()));
            List<AttrEntity> attrList = relationList.stream().map(re -> attrService.getById(re.getAttrId())).collect(Collectors.toList());
            agv.setAttrList(attrList);
            return agv;
        }).collect(Collectors.toList());
    }

    @Override
    public Set<Long> removeByGroupIds(List<Long> attrGroupIds) {
        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("attr_group_id", attrGroupIds);
        List<AttrAttrgroupRelationEntity> existList = attrAttrgroupRelationService.list(queryWrapper);
        Set<Long> existIdSet = new HashSet<>();
        if (existList.size() > 0) {
            existIdSet = existList.stream().map(entity -> entity.getAttrGroupId()).collect(Collectors.toSet());
            attrGroupIds.removeAll(existIdSet);
        }
        this.removeBatchByIds(attrGroupIds);
        return existIdSet;
    }

    @Override
    public List<SpuItemAttrGroupVo> listGetAttrGroupAndAttrBySpuId(Long spuId, Long catalogId) {
        return baseMapper.listGetAttrGroupAndAttrBySpuId(spuId, catalogId);
    }
}