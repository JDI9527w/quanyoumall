package com.wzy.quanyoumall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单操作历史记录
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@Data
@TableName("oms_order_operate_history")
public class OrderOperateHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 操作人[用户；系统；后台管理员]
     */
    private String operateMan;
    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
    private Integer orderStatus;
    /**
     * 备注
     */
    private String note;

}
