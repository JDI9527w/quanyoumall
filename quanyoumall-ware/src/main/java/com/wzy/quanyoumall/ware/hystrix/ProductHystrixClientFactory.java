package com.wzy.quanyoumall.ware.hystrix;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.feign.ProductFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductHystrixClientFactory implements FallbackFactory<ProductFeignService> {
    @Override
    public ProductFeignService create(Throwable cause) {
        log.error("发生异常{}","异常信息{}",cause,cause.getMessage());
        return (ProductFeignServiceWithFactory) skuId -> R.ok();
    }
}
