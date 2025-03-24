package com.wzy.quanyoumall.order.vo;

import com.wzy.quanyoumall.order.entity.OrderEntity;
import lombok.Data;

@Data
public class SubmitOrderRespVo {

    private OrderEntity order;

    /**
     * 错误状态码
     **/
    private Integer code;
}