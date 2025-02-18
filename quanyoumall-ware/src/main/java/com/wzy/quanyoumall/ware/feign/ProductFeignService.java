package com.wzy.quanyoumall.ware.feign;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.hystrix.ProductHystrixClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "quanyoumall-product",fallbackFactory = ProductHystrixClientFactory.class)
public interface ProductFeignService {

    @RequestMapping("product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
