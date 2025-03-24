package com.wzy.quanyoumall.order.feign;

import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("quanyoumall-product")
public interface ProductFeignService {
    @GetMapping("/product/spuinfo/spuInfo")
    R getSpuInfoBySkuId(@RequestParam("skuId") Long skuId);
}
