package com.wzy.quanyoumall.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("quanyoumall-member")
public interface memberFeign {
}
