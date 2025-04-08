package com.wzy.quanyoumall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.coupon.entity.SeckillSessionEntity;
import com.wzy.quanyoumall.coupon.entity.SeckillSkuRelationEntity;
import com.wzy.quanyoumall.coupon.mapper.SeckillSessionMapper;
import com.wzy.quanyoumall.coupon.service.SeckillSessionService;
import com.wzy.quanyoumall.coupon.service.SeckillSkuRelationService;
import com.wzy.quanyoumall.coupon.to.SeckillSessionTo;
import com.wzy.quanyoumall.coupon.vo.SessionQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSessionEntity> implements SeckillSessionService {
    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public List<SeckillSessionTo> get3DaysSeckillSession() {
        List<SeckillSessionTo> seckillSessionToList = baseMapper.select3DaysSeckillSession(getStartTime(), getEndTime());
        if (seckillSessionToList != null && seckillSessionToList.size() > 0) {
            List<Long> sessionIds = seckillSessionToList.stream().map(SeckillSessionTo::getId).collect(Collectors.toList());
            List<SeckillSkuRelationEntity> allRelationSku = seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>().in("promotion_session_id", sessionIds));
            if (allRelationSku != null && allRelationSku.size() > 0) {
                seckillSessionToList.forEach(seckillSessionEntity -> {
                    List<SeckillSkuRelationEntity> collect = allRelationSku.stream().filter(skuItem -> skuItem.getPromotionSessionId().equals(seckillSessionEntity.getId())).collect(Collectors.toList());
                    seckillSessionEntity.setRelations(collect);
                });
            }
        }
        return seckillSessionToList;
    }

    public String getStartTime() {
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getEndTime() {
        LocalDate endDate = LocalDate.now().plusDays(2);
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);
        return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public Page<SeckillSessionEntity> queryPage(SessionQueryVo sessionQueryVo, Page<SeckillSessionEntity> page) {
        QueryWrapper<SeckillSessionEntity> wrapper = new QueryWrapper<>();
        if (sessionQueryVo != null) {
            if (StringUtils.isNotBlank(sessionQueryVo.getSessionName())) {
                wrapper.eq("name", sessionQueryVo.getSessionName());
            }
        }
        Page<SeckillSessionEntity> seckillSessionEntityPage = baseMapper.selectPage(page, wrapper);
        return seckillSessionEntityPage;
    }
}