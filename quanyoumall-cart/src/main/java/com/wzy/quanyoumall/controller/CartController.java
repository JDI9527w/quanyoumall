package com.wzy.quanyoumall.controller;

import com.wzy.quanyoumall.interceptor.CartInterceptor;
import com.wzy.quanyoumall.service.impl.CartServiceImpl;
import com.wzy.quanyoumall.vo.CartItemVo;
import com.wzy.quanyoumall.vo.UserInfoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @RequestMapping("/cart.html")
    public String cartList() throws Exception {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            return "cartList";
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }

    @GetMapping("/addCartItem")
    public String addCartItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes ra) throws ExecutionException, InterruptedException {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            cartService.addCartItem(skuId, num, userInfoVo);
            ra.addAttribute("skuId", skuId);
            return "redirect://8.152.219.128/cart/success.html";
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }

    @GetMapping("/success.html")
    public String addCartItemSuccess(@RequestParam("skuId") Long skuId, Model model) {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        CartItemVo cartItemVo = cartService.getCartItemBySkuId(skuId, userInfoVo);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }
}
