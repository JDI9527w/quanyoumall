package com.wzy.quanyoumall.authServer.controller;

import com.wzy.quanyoumall.authServer.vo.CartItemVo;
import com.wzy.quanyoumall.authServer.vo.CartVo;
import com.wzy.quanyoumall.authServer.vo.UserInfoVo;
import com.wzy.quanyoumall.interceptor.CartInterceptor;
import com.wzy.quanyoumall.service.impl.CartServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @RequestMapping("/cart.html")
    public String cartList(Model model) throws Exception {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            CartVo cartVo = cartService.getCartByUser(userInfoVo);
            model.addAttribute("cart", cartVo);
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

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("checked") Integer checked) {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setSkuId(skuId);
            cartItemVo.setCheck(checked == 0 ? false : true);
            cartService.updateUserCartItem(cartItemVo, userInfoVo);
            return "redirect://8.152.219.128/cart/cart.html";
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setSkuId(skuId);
            cartItemVo.setCount(num);
            cartService.updateUserCartItem(cartItemVo, userInfoVo);
            return "redirect://8.152.219.128/cart/cart.html";
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        UserInfoVo userInfoVo = CartInterceptor.threadLocal.get();
        if (ObjectUtils.isNotEmpty(userInfoVo)) {
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setSkuId(skuId);
            cartService.deleteUserCartItem(skuId, userInfoVo);
            return "redirect://8.152.219.128/cart/cart.html";
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }

    @ResponseBody
    @GetMapping("/checkedItems")
    public List<CartItemVo> listGetCheckedItems(@RequestParam("memberId") Long memberId) {
        return cartService.listGetCheckedItems(memberId);
    }
}
