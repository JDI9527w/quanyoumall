package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.SkuSaleAttrValueEntity;
import com.wzy.quanyoumall.product.mapper.SkuSaleAttrValueMapper;
import com.wzy.quanyoumall.product.service.SkuSaleAttrValueService;
import com.wzy.quanyoumall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public List<SkuItemSaleAttrVo> listGetsaleAttrsBySpuId(Long spuId) {
        return baseMapper.listGetsaleAttrsBySpuId(spuId);
    }

}