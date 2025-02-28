package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.common.utils.Query;
import com.wzy.quanyoumall.product.entity.SkuSaleAttrValueEntity;
import com.wzy.quanyoumall.product.mapper.SkuSaleAttrValueMapper;
import com.wzy.quanyoumall.product.service.SkuSaleAttrValueService;
import com.wzy.quanyoumall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVo> listGetsaleAttrsBySpuId(Long spuId) {
        return baseMapper.listGetsaleAttrsBySpuId(spuId);
    }

}