package com.wzy.quanyoumall.product.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.SkuSaleAttrValueEntity;
import com.wzy.quanyoumall.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * sku销售属性&值
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
public class SkuSaleAttrValueController {
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 列表
     */


    @GetMapping("/getSaleAttrListBySkuId")
    public R getSaleAttrListBySkuId(@RequestParam("skuId") Long skuId) {
        List<String> saleAttrList = skuSaleAttrValueService.getSaleAttrListBySkuId(skuId);
        return R.ok().put("data", saleAttrList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SkuSaleAttrValueEntity skuSaleAttrValue = skuSaleAttrValueService.getById(id);

        return R.ok().put("skuSaleAttrValue", skuSaleAttrValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
        skuSaleAttrValueService.save(skuSaleAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
        skuSaleAttrValueService.updateById(skuSaleAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        skuSaleAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
