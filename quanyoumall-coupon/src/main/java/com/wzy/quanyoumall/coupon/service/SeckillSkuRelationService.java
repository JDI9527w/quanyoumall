package com.wzy.quanyoumall.coupon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.coupon.entity.SeckillSkuRelationEntity;
import com.wzy.quanyoumall.coupon.vo.SkuRelationQueryVo;

/**
 * 秒杀活动商品关联
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:34:21
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    Page<SeckillSkuRelationEntity> queryPage(SkuRelationQueryVo skuRelationQueryVo, Page<SeckillSkuRelationEntity> page);
}

