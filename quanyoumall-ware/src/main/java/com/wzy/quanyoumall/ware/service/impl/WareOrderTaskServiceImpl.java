package com.wzy.quanyoumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskEntity;
import com.wzy.quanyoumall.ware.mapper.WareOrderTaskMapper;
import com.wzy.quanyoumall.ware.service.WareOrderTaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Service
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTaskEntity> implements WareOrderTaskService {

    @Override
    public Page<WareOrderTaskEntity> queryPage(WareOrderTaskEntity wareOrderTaskEntity, Page<WareOrderTaskEntity> page) {
        QueryWrapper<WareOrderTaskEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(wareOrderTaskEntity)) {
            if (wareOrderTaskEntity.getOrderId() != null) {
                queryWrapper.eq("order_id", wareOrderTaskEntity.getOrderId());
            }
            if (StringUtils.isNotBlank(wareOrderTaskEntity.getConsigneeTel())) {
                queryWrapper.like("consignee_tel", wareOrderTaskEntity.getConsigneeTel());
            }
            if (StringUtils.isNotBlank(wareOrderTaskEntity.getTrackingNo())) {
                queryWrapper.eq("tracking_no", wareOrderTaskEntity.getTrackingNo());
            }
            if (StringUtils.isNotBlank(wareOrderTaskEntity.getDeliveryAddress())) {
                queryWrapper.like("delivery_address", wareOrderTaskEntity.getDeliveryAddress());
            }
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public WareOrderTaskEntity getTaskByOrderSn(String orderSn) {
        return baseMapper.selectOne(new QueryWrapper<WareOrderTaskEntity>().eq("order_sn", orderSn).last("limit 1"));
    }
}