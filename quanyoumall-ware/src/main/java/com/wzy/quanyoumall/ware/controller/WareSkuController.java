package com.wzy.quanyoumall.ware.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.exception.BizCodeEnum;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import com.wzy.quanyoumall.ware.service.WareSkuService;
import com.wzy.quanyoumall.ware.vo.FareVo;
import com.wzy.quanyoumall.ware.vo.WareSkuLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(WareSkuEntity wareSkuEntity,
                  @RequestParam Integer pageNum,
                  @RequestParam Integer pageSize) {
        Page<WareSkuEntity> page = new Page<>(pageNum, pageSize);
        Page<WareSkuEntity> result = wareSkuService.queryPage(wareSkuEntity, page);

        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);
        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 通过skuid查询库存信息
     */
    @PostMapping("/infoBySkuId")
    public R infoBySkuId(@RequestBody List<Long> skuIds) {
        List<SkuStockTO> hasStokeBySkuIds = wareSkuService.getHasStokeBySkuIds(skuIds);
        return R.ok().put("data", hasStokeBySkuIds);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/fare/{addrId}")
    public FareVo getFareByAddrId(@PathVariable("addrId") Long addrId) {
        return wareSkuService.getFareByAddrId(addrId);
    }

    @PostMapping("/lockStock")
    public R lockStockByLockVo(@RequestBody WareSkuLockVo lockVo) {
        boolean flag = wareSkuService.lockStockByLockVo(lockVo);
        if (flag) {
            return R.ok();
        }
        return R.error(BizCodeEnum.OUT_OF_STOCK_EXCEPTION.getCode(), BizCodeEnum.OUT_OF_STOCK_EXCEPTION.getMsg());
    }
}
