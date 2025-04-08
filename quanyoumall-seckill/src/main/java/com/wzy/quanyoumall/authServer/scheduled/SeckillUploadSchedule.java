package com.wzy.quanyoumall.authServer.scheduled;

import com.wzy.quanyoumall.authServer.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableScheduling
@EnableAsync
public class SeckillUploadSchedule {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SeckillService seckillService;
    private static final String UPLOAD_LOCK = "seckill:upload:lock";

//    @Scheduled(cron = "* * * * * ?")
//    public void test() {
//        log.info("test");
//    }

    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void upload3DaysSeckillSession() {
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("开始更新秒杀商品信息");
            seckillService.upload3DaysSeckillSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
