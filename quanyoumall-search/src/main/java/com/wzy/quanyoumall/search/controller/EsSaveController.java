package com.wzy.quanyoumall.search.controller;

import com.wzy.quanyoumall.common.exception.bizCodeEnum;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.search.service.ProductSaveService;
import com.wzy.quanyoumall.search.vo.SkuEsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/es/save")
public class EsSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productUp(@RequestBody List<SkuEsVo> skuEsVoList) {
        boolean flag;
        try {
            flag = productSaveService.productUp(skuEsVoList);
        } catch (IOException e) {
            flag = false;
            log.error(e.getMessage(), e.getStackTrace(), e.getClass());
        }
        if (flag) {
            return R.ok();
        }
        return R.error(bizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), bizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
    }
}
