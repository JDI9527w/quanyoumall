package com.wzy.quanyoumall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import com.wzy.quanyoumall.order.mapper.OrderMapper;
import com.wzy.quanyoumall.order.service.OrderService;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {


}