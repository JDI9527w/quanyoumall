package com.wzy.quanyoumall.product.web;

import com.wzy.quanyoumall.product.service.SkuInfoService;
import com.wzy.quanyoumall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/product/item")
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 展示指定商品详情.
     *
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String itemDetail(@PathVariable Long skuId, Model model) {
        SkuItemVo skuItemVo = skuInfoService.getSkuDetailById(skuId);
        model.addAttribute("item", skuItemVo);
        return "item";
    }
}