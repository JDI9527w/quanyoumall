package com.wzy.quanyoumall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.order.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author wzy
 * @email 
 * @date 2025-01-05 21:37:50
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

