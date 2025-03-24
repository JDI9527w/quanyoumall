package com.wzy.quanyoumall.order.feign;

import com.wzy.quanyoumall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("quanyoumall-cart")
public interface CartFeignService {

    @GetMapping("/cart/checkedItems")
    List<OrderItemVo> listGetCheckedItems(@RequestParam("memberId") Long memberId);
}
