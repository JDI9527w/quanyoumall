package com.wzy.quanyoumall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.coupon.entity.SeckillSessionEntity;
import com.wzy.quanyoumall.coupon.to.SeckillSessionTo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 秒杀活动场次
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:34:21
 */
@Mapper
public interface SeckillSessionMapper extends BaseMapper<SeckillSessionEntity> {

    List<SeckillSessionTo> select3DaysSeckillSession(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
