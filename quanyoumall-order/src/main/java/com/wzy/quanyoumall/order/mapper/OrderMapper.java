package com.wzy.quanyoumall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    Page<OrderEntity> pageByQueryParam(@Param("objectPage") Page<OrderEntity> objectPage,
                                       @Param("queryParam") String queryParam,
                                       @Param("memberId") Long memberId);
}
