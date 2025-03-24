package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;

import java.util.List;

/**
 * 库存工作单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    /**
     * 通过库存工作单id获取库存工作单详情
     *
     * @param taskId
     * @return
     */
    List<WareOrderTaskDetailEntity> listByTaskId(Long taskId);

}

