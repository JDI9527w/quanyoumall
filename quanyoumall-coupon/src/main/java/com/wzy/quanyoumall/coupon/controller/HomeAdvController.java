package com.wzy.quanyoumall.coupon.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.coupon.entity.HomeAdvEntity;
import com.wzy.quanyoumall.coupon.service.HomeAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 首页轮播广告
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:34:21
 */
@RestController
@RequestMapping("coupon/homeadv")
public class HomeAdvController {
    @Autowired
    private HomeAdvService homeAdvService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        HomeAdvEntity homeAdv = homeAdvService.getById(id);

        return R.ok().put("homeAdv", homeAdv);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.save(homeAdv);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.updateById(homeAdv);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        homeAdvService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
