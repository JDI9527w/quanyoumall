package com.wzy.quanyoumall.ware.feign;

import com.wzy.quanyoumall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("quanyoumall-order")
public interface OrderFeignService {
    /**
     * 根据sn码查询订单信息
     *
     * @param orderSn
     * @return
     */
    @GetMapping("/order/order/info/{orderSn}")
    R infoBySn(@PathVariable("orderSn") String orderSn);
}
