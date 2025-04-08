package com.wzy.quanyoumall.authServer.feign;

import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("quanyoumall-product")
public interface ProductFeignService {

    @GetMapping("/product/skuinfo/queryListByIds")
    R queryListByIds(@RequestParam("skuIds")List<Long> skuIds);

}
