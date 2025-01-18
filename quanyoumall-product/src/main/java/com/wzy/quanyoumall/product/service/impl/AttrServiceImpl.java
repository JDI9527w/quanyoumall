package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.constant.ProductConstant;
import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.mapper.AttrMapper;
import com.wzy.quanyoumall.product.service.AttrAttrgroupRelationService;
import com.wzy.quanyoumall.product.service.AttrGroupService;
import com.wzy.quanyoumall.product.service.AttrService;
import com.wzy.quanyoumall.product.service.CategoryService;
import com.wzy.quanyoumall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService aars;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public Page<AttrVo> queryPage(Page<AttrEntity> page, AttrEntity attrEntity, Integer selectType) {
        return baseMapper.queryPage(page, attrEntity, selectType);
    }

    @Override
    public void saveAndThen(AttrVo attrVo) {
        AttrEntity ae = new AttrEntity();
        BeanUtils.copyProperties(attrVo, ae);
        baseMapper.insert(ae);
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity aar = new AttrAttrgroupRelationEntity();
            aar.setAttrId(ae.getAttrId());
            aar.setAttrGroupId(attrVo.getAttrGroupId());
            aars.save(aar);
        }
    }

    @Override
    public void removeByIdsAndThen(List<Long> attrIds) {
        baseMapper.deleteBatchIds(attrIds);
        aars.deleteByAttrIds(attrIds);
    }

    @Override
    public AttrVo getDetailById(Long attrId) {
        AttrEntity attr = baseMapper.selectById(attrId);
        AttrAttrgroupRelationEntity aar = aars.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        List<Long> catelogPath = categoryService.queryLevelCidsBycatelogId(attr.getCatelogId());
        AttrVo av = new AttrVo();
        BeanUtils.copyProperties(attr, av);
        Collections.reverse(catelogPath);
        av.setCatelogPath(catelogPath);
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            av.setAttrGroupId(aar.getAttrGroupId());
        }
        return av;
    }

    @Override
    public void updateByIdAndThen(AttrVo attrVo) {
        AttrEntity ae = new AttrEntity();
        BeanUtils.copyProperties(attrVo, ae);
        baseMapper.updateById(ae);
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity aar = aars.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", ae.getAttrId()));
            aar.setAttrId(ae.getAttrId());
            aar.setAttrGroupId(attrVo.getAttrGroupId());
            aars.updateById(aar);
        }

    }

}