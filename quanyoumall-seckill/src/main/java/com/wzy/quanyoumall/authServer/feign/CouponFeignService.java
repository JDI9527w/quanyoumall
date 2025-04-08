package com.wzy.quanyoumall.authServer.feign;

import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("quanyoumall-coupon")
public interface CouponFeignService {
    @GetMapping("/coupon/seckillsession/get3DaysSeckillSession")
    R get3DaysSeckillSession();
}
