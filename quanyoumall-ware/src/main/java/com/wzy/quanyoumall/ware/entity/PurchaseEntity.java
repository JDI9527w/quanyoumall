package com.wzy.quanyoumall.ware.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@Data
@TableName("wms_purchase")
public class PurchaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 采购单id
     */
    @TableId
    private Long id;
    /**
     * 采购人id
     */
    private Long assigneeId;
    /**
     * 采购人名
     */
    private String assigneeName;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 状态
     * 新建:0
     * 已分配:1
     * 已领取:2
     * 已完成:3
     * 有异常:4
     */
    private Integer status;
    /**
     * 仓库id
     */
    private Long wareId;
    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
