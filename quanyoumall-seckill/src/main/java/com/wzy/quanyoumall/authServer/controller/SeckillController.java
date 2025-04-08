package com.wzy.quanyoumall.authServer.controller;

import com.wzy.quanyoumall.authServer.service.SeckillService;
import com.wzy.quanyoumall.authServer.to.SeckillSkuRedisTo;
import com.wzy.quanyoumall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @GetMapping("/getCurrentSeckillSku")
    public R getCurrentSeckillSku() {
        List<SeckillSkuRedisTo> seckillSkuRedisToList = seckillService.getCurrentSeckillSku();
        return R.ok().put("data", seckillSkuRedisToList);
    }
}
