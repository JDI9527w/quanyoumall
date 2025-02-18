package com.wzy.quanyoumall.product.web;

import com.wzy.quanyoumall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;
}