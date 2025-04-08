package com.wzy.quanyoumall.coupon.vo;

import lombok.Data;

@Data
public class SessionQueryVo {
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 秒杀活动场次名称
     */
    private String sessionName;
}
