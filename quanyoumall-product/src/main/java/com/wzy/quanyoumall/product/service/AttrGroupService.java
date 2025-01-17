package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;

/**
 * 属性分组
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    Page<AttrGroupEntity> queryPage(Page<AttrGroupEntity> page, AttrGroupEntity attrGroupEntity);


}

