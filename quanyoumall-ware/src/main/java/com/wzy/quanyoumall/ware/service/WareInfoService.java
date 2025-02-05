package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.WareInfoEntity;

/**
 * 仓库信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    Page<WareInfoEntity> queryPage(WareInfoEntity wareInfoEntity, Page<WareInfoEntity> page);
}

