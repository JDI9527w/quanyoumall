package com.wzy.quanyoumall.authServer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.wzy.quanyoumall.authServer.feign.CouponFeignService;
import com.wzy.quanyoumall.authServer.feign.ProductFeignService;
import com.wzy.quanyoumall.authServer.service.SeckillService;
import com.wzy.quanyoumall.authServer.to.SeckillSessionWithSkuTo;
import com.wzy.quanyoumall.authServer.to.SeckillSkuInfoTo;
import com.wzy.quanyoumall.authServer.to.SeckillSkuRedisTo;
import com.wzy.quanyoumall.authServer.to.SeckillSkuRelationTo;
import com.wzy.quanyoumall.common.utils.R;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private final String SECKILL_SESSION_CACHE_PREFIX = "seckill:sessions:";
    private final String SECKILL_SKU_CACHE_PREFIX = "seckill:sku";
    private final String SECKILL_SKU_STOCK_SEMAPHORE = "seckill:skustock:";

    @Override
    public void upload3DaysSeckillSession() {
        R res = couponFeignService.get3DaysSeckillSession();
        if (res.getCode() == 0) {
            List<SeckillSessionWithSkuTo> seckillSessionWithSkusTos = res.getData(new TypeReference<List<SeckillSessionWithSkuTo>>() {
            });
            if (seckillSessionWithSkusTos != null && seckillSessionWithSkusTos.size() > 0) {
                this.cacheSeckillSessionsInfo(seckillSessionWithSkusTos);
                this.cacheSeckillSessionSkusInfo(seckillSessionWithSkusTos);
            }
        }
    }

    /**
     * 缓存秒杀场次与商品id信息
     *
     * @param seckillSessionWithSkusTos
     */
    private void cacheSeckillSessionsInfo(List<SeckillSessionWithSkuTo> seckillSessionWithSkusTos) {
        seckillSessionWithSkusTos.forEach(seckillSessionWithSkuTo -> {
            long startTime = seckillSessionWithSkuTo.getStartTime().atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
            long endTime = seckillSessionWithSkuTo.getEndTime().atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
            String key = SECKILL_SESSION_CACHE_PREFIX + startTime + "_" + endTime;
            Boolean hasKey = redisTemplate.hasKey(key);
            if (ObjectUtils.isNotEmpty(hasKey) && !hasKey) {
                List<SeckillSkuRelationTo> relations = seckillSessionWithSkuTo.getRelations();
                if (relations != null && relations.size() > 0) {
                    List<String> skuIds = seckillSessionWithSkuTo.getRelations().stream().map(skurelationTo -> {
                        String sessionId = skurelationTo.getPromotionSessionId().toString();
                        String skuId = skurelationTo.getSkuId().toString();
                        return sessionId + "_" + skuId;
                    }).collect(Collectors.toList());
                    // 活动场次id_商品id
                    redisTemplate.opsForList().leftPushAll(key, skuIds);
                }
            }
        });
    }

    /**
     * 缓存秒杀商品详细信息
     *
     * @param seckillSessionWithSkusTos
     */
    private void cacheSeckillSessionSkusInfo(List<SeckillSessionWithSkuTo> seckillSessionWithSkusTos) {
        List<Long> skuIds = seckillSessionWithSkusTos.stream().map(SeckillSessionWithSkuTo::getRelations)
                .flatMap(List::stream).map(SeckillSkuRelationTo::getSkuId).collect(Collectors.toList());
        if (skuIds != null && skuIds.size() > 0) {
            R r = productFeignService.queryListByIds(skuIds);
            if (r.getCode() == 0) {
                List<SeckillSkuInfoTo> skuInfoList = r.getData(new TypeReference<List<SeckillSkuInfoTo>>() {
                });
                Map<Long, SeckillSkuInfoTo> skusMap = skuInfoList.stream().collect(Collectors.toMap(item -> item.getSkuId(), item -> item));
                seckillSessionWithSkusTos.stream().forEach(seckillSessionWithSkuTo -> {
                    BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(SECKILL_SKU_CACHE_PREFIX);
                    seckillSessionWithSkuTo.getRelations().forEach(seckillSkuRelationTo -> {
                        String redisKey = seckillSessionWithSkuTo.getId() + "_" + seckillSkuRelationTo.getSkuId();
                        Boolean hasKey = hashOps.hasKey(redisKey);
                        if (hasKey != null && !hasKey) {
                            SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
                            SeckillSkuInfoTo seckillSkuInfoTo = skusMap.get(seckillSkuRelationTo.getSkuId());
                            seckillSkuRedisTo.setSkuInfoTo(seckillSkuInfoTo);
                            BeanUtils.copyProperties(seckillSkuRelationTo, seckillSkuRedisTo);
                            seckillSkuRedisTo.setStartTime(seckillSessionWithSkuTo.getStartTime().atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
                            seckillSkuRedisTo.setEndTime(seckillSessionWithSkuTo.getEndTime().atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
                            String randomCode = UUID.randomUUID().toString().replace("-", "");
                            seckillSkuRedisTo.setRandomCode(randomCode);

                            // 设置秒杀商品的信号量 限流.
                            RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_SKU_STOCK_SEMAPHORE + randomCode);
                            semaphore.trySetPermits(seckillSkuRelationTo.getSeckillCount());

                            String jsonString = JSON.toJSONString(seckillSkuRedisTo);
                            hashOps.put(redisKey, jsonString);
                        }
                    });
                });
            }
        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSku() {
        Set<String> keys = redisTemplate.keys(SECKILL_SESSION_CACHE_PREFIX + "*");
        long nowTime = new Date().getTime();
        if (ObjectUtils.isNotEmpty(keys)) {
            for (String key : keys) {
                String[] split = key.replace(SECKILL_SESSION_CACHE_PREFIX, "").split("_");
                String startTime = split[0];
                String endTime = split[1];
                if (nowTime >= Long.parseLong(startTime) && nowTime <= Long.parseLong(endTime)) {
                    List<String> seckillSessionSkus = redisTemplate.opsForList().range(key, 0, -1);
                    if (ObjectUtils.isNotEmpty(seckillSessionSkus) && seckillSessionSkus.size() > 0) {
                        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_SKU_CACHE_PREFIX);
                        List<String> objectList = hashOps.multiGet(seckillSessionSkus);
                        if (ObjectUtils.isNotEmpty(objectList) && objectList.size() > 0) {
                            List<SeckillSkuRedisTo> seckillSkuRedisTos = objectList.stream().map(item -> {
                                SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(item, SeckillSkuRedisTo.class);
                                return seckillSkuRedisTo;
                            }).collect(Collectors.toList());
                            return seckillSkuRedisTos;
                        }
                    }
                }
            }
        }
        return null;
    }
}
