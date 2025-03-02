package com.wzy.quanyoumall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.order.entity.OrderItemEntity;
import com.wzy.quanyoumall.order.mapper.OrderItemMapper;
import com.wzy.quanyoumall.order.service.OrderItemService;
import org.springframework.stereotype.Service;


@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemEntity> implements OrderItemService {


}