package com.wzy.quanyoumall.authServer.to;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeckillSessionWithSkuTo {
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 每日开始时间
     */
    private LocalDateTime startTime;
    /**
     * 每日结束时间
     */
    private LocalDateTime endTime;
    /**
     * 启用状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 秒杀商品
     */
    private List<SeckillSkuRelationTo> relations;
}
