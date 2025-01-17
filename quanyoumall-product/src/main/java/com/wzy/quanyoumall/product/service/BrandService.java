package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.BrandEntity;

/**
 * 品牌
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface BrandService extends IService<BrandEntity> {

    Page<BrandEntity> queryPage(Page<BrandEntity> page, BrandEntity brandEntity);

    /**
     * 修改关联的其他表字段
     *
     * @param brand
     */
    void updateOtherName(BrandEntity brand);


    void updateAndThen(BrandEntity brand);
}

