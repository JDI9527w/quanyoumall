package com.wzy.quanyoumall.order.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import com.wzy.quanyoumall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 订单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    @GetMapping("/info/{orderSn}")
    public R infoBySn(@PathVariable("orderSn") String orderSn) {
        OrderEntity order = orderService.getByOrderSn(orderSn);
        return R.ok().put("data", order);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderEntity order) {
        orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderEntity order) {
        orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
