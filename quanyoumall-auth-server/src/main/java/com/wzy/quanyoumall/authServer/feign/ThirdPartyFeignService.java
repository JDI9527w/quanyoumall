package com.wzy.quanyoumall.authServer.feign;

import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("quanyoumall-third-party")
public interface ThirdPartyFeignService {

    @GetMapping("/sms/sendCode")
    R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
