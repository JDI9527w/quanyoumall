package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;
import com.wzy.quanyoumall.ware.mapper.WareOrderTaskDetailMapper;
import com.wzy.quanyoumall.ware.service.WareOrderTaskDetailService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetailEntity> implements WareOrderTaskDetailService {

    @Override
    public List<WareOrderTaskDetailEntity> listByTaskId(Long taskId) {
        return baseMapper.selectList(new QueryWrapper<WareOrderTaskDetailEntity>().eq("task_id", taskId));
    }
}