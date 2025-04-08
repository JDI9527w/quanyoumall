package com.wzy.quanyoumall.coupon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.coupon.entity.SeckillSessionEntity;
import com.wzy.quanyoumall.coupon.to.SeckillSessionTo;
import com.wzy.quanyoumall.coupon.vo.SessionQueryVo;

import java.util.List;

/**
 * 秒杀活动场次
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:34:21
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {


    /**
     * 获取三天的秒杀活动场次
     *
     * @return
     */
    List<SeckillSessionTo> get3DaysSeckillSession();

    /**
     * 秒杀活动分页查询
     *
     * @param sessionQueryVo
     * @param page
     * @return
     */
    Page<SeckillSessionEntity> queryPage(SessionQueryVo sessionQueryVo, Page<SeckillSessionEntity> page);
}

