package com.wzy.quanyoumall.authServer.service;

import com.wzy.quanyoumall.authServer.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    /**
     * 更新三天内的秒杀活动信息
     */
    void upload3DaysSeckillSession();

    /**
     * 查询当前时间段秒杀商品信息
     *
     * @return
     */
    List<SeckillSkuRedisTo> getCurrentSeckillSku();
}
