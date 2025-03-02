package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.ProductAttrValueEntity;
import com.wzy.quanyoumall.product.mapper.ProductAttrValueMapper;
import com.wzy.quanyoumall.product.service.AttrService;
import com.wzy.quanyoumall.product.service.ProductAttrValueService;
import com.wzy.quanyoumall.product.vo.BaseAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;

    @Override
    public void saveAttrBatch(Long spuId, List<BaseAttrVo> baseAttrs) {
        if (baseAttrs != null && baseAttrs.size() > 0) {
            List<ProductAttrValueEntity> paveList = baseAttrs.stream().map(baseAttr -> {
                ProductAttrValueEntity pave = new ProductAttrValueEntity();
                pave.setSpuId(spuId);
                pave.setAttrId(baseAttr.getAttrId());
                AttrEntity attr = attrService.getById(baseAttr.getAttrId());
                pave.setAttrName(attr.getAttrName());
                pave.setQuickShow(baseAttr.getShowDesc());
                pave.setAttrValue(baseAttr.getAttrValues());
                return pave;
            }).collect(Collectors.toList());
            this.saveBatch(paveList);
        }
    }

    @Override
    public List<ProductAttrValueEntity> getAttrListBySpuId(Long spuId) {
        return baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
    }

    @Override
    @Transactional
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> paveList) {
        baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        paveList.forEach(item -> {
            item.setSpuId(spuId);
        });
        this.saveBatch(paveList);
    }


    @Override
    public List<ProductAttrValueEntity> listGetNeedSearchAttrBySpuId(Long spuId) {
        return baseMapper.listGetNeedSearchAttrBySpuId(spuId);
    }
}