package com.wzy.quanyoumall.order.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.order.entity.OrderSettingEntity;
import com.wzy.quanyoumall.order.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 订单配置信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@RestController
@RequestMapping("order/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderSettingEntity orderSetting = orderSettingService.getById(id);

        return R.ok().put("orderSetting", orderSetting);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderSettingEntity orderSetting) {
        orderSettingService.save(orderSetting);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderSettingEntity orderSetting) {
        orderSettingService.updateById(orderSetting);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderSettingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
