package com.wzy.quanyoumall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@Data
@TableName("undo_log")
public class UndoLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long branchId;
    /**
     *
     */
    private String xid;
    /**
     *
     */
    private String context;
    /**
     *
     */
    private String rollbackInfo;
    /**
     *
     */
    private Integer logStatus;
    /**
     *
     */
    private LocalDateTime logCreated;
    /**
     *
     */
    private LocalDateTime logModified;
    /**
     *
     */
    private String ext;

}
