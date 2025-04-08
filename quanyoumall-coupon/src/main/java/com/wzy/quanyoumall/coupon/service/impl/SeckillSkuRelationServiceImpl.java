package com.wzy.quanyoumall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.coupon.entity.SeckillSkuRelationEntity;
import com.wzy.quanyoumall.coupon.mapper.SeckillSkuRelationMapper;
import com.wzy.quanyoumall.coupon.service.SeckillSkuRelationService;
import com.wzy.quanyoumall.coupon.vo.SkuRelationQueryVo;
import org.springframework.stereotype.Service;


@Service
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationMapper, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public Page<SeckillSkuRelationEntity> queryPage(SkuRelationQueryVo skuRelationQueryVo, Page<SeckillSkuRelationEntity> page) {
        QueryWrapper<SeckillSkuRelationEntity> queryWrapper = new QueryWrapper<>();
        if (skuRelationQueryVo != null) {
            if (skuRelationQueryVo.getPromotionSessionId() != null) {
                queryWrapper.eq("promotion_session_id", skuRelationQueryVo.getPromotionSessionId());
            }
            if (skuRelationQueryVo.getSkuId() != null) {
                queryWrapper.eq("sku_id", skuRelationQueryVo.getPromotionSessionId());
            }
        }
        Page<SeckillSkuRelationEntity> seckillSkuRelationEntityPage = baseMapper.selectPage(page, queryWrapper);
        return seckillSkuRelationEntityPage;
    }
}