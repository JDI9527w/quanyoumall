package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.ware.entity.WareInfoEntity;
import com.wzy.quanyoumall.ware.mapper.WareInfoMapper;
import com.wzy.quanyoumall.ware.service.WareInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfoEntity> implements WareInfoService {

    @Override
    public Page<WareInfoEntity> queryPage(WareInfoEntity wareInfoEntity, Page<WareInfoEntity> page) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(wareInfoEntity)) {
            if (StringUtils.isNotBlank(wareInfoEntity.getName())) {
                queryWrapper.eq("name", wareInfoEntity.getName())
                        .or()
                        .eq("address", wareInfoEntity.getName());
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }
}