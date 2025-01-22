package com.wzy.quanyoumall.product.feign;

import com.wzy.quanyoumall.common.to.SkuReductionTo;
import com.wzy.quanyoumall.common.to.SpuBoundsTo;
import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("quanyoumall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("coupon/skufullreduction/saveSkuReductionTo")
    R saveSkuReductionTo(@RequestBody SkuReductionTo skuReductionTo);
}
