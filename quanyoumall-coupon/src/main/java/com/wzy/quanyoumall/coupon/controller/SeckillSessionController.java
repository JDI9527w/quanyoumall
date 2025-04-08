package com.wzy.quanyoumall.coupon.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.coupon.entity.SeckillSessionEntity;
import com.wzy.quanyoumall.coupon.service.SeckillSessionService;
import com.wzy.quanyoumall.coupon.to.SeckillSessionTo;
import com.wzy.quanyoumall.coupon.vo.SessionQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 秒杀活动场次
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:34:21
 */
@RestController
@RequestMapping("coupon/seckillsession")
public class SeckillSessionController {
    @Autowired
    private SeckillSessionService seckillSessionService;

    /**
     * 列表查询
     *
     * @return
     */
    @GetMapping("/list")
    public R list(SessionQueryVo sessionQueryVo,
                  @RequestParam Integer pageNum,
                  @RequestParam Integer pageSize) {
        Page<SeckillSessionEntity> page = new Page<>(pageNum, pageSize);
        Page<SeckillSessionEntity> result = seckillSessionService.queryPage(sessionQueryVo, page);
        return R.ok().put("data", result);
    }

    /**
     * 获取三天的秒杀活动场次
     *
     * @return
     */
    @GetMapping("/get3DaysSeckillSession")
    public R get3DaysSeckillSession() {
        List<SeckillSessionTo> sessionList = seckillSessionService.get3DaysSeckillSession();
        return R.ok().put("data", sessionList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SeckillSessionEntity seckillSession = seckillSessionService.getById(id);

        return R.ok().put("seckillSession", seckillSession);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SeckillSessionEntity seckillSession) {
        seckillSessionService.save(seckillSession);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SeckillSessionEntity seckillSession) {
        seckillSessionService.updateById(seckillSession);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        seckillSessionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
