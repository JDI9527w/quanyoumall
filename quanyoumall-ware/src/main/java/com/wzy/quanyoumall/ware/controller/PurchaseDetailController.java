package com.wzy.quanyoumall.ware.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzy.quanyoumall.common.to.SkuDTO;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.PurchaseDetailEntity;
import com.wzy.quanyoumall.ware.feign.ProductFeignService;
import com.wzy.quanyoumall.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;


/**
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private ProductFeignService productFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R queryPageList(PurchaseDetailEntity purchaseDetailEntity,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        Page<PurchaseDetailEntity> page = new Page<>(pageNum, pageSize);
        Page<PurchaseDetailEntity> result = purchaseDetailService.queryPage(purchaseDetailEntity, page);

        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail) {
        R resR = productFeignService.info(purchaseDetail.getSkuId());
        SkuDTO skuDTO = new ObjectMapper().convertValue(resR.get("skuInfo"), SkuDTO.class);
        BigDecimal sumPrice = skuDTO.getPrice().multiply(new BigDecimal(purchaseDetail.getSkuNum()));
        purchaseDetail.setSkuPrice(sumPrice);
        purchaseDetailService.save(purchaseDetail);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail) {
        R resR = productFeignService.info(purchaseDetail.getSkuId());
        SkuDTO skuDTO = new ObjectMapper().convertValue(resR.get("skuInfo"), SkuDTO.class);
        BigDecimal sumPrice = skuDTO.getPrice().multiply(new BigDecimal(purchaseDetail.getSkuNum()));
        purchaseDetail.setSkuPrice(sumPrice);
        purchaseDetailService.updateById(purchaseDetail);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
