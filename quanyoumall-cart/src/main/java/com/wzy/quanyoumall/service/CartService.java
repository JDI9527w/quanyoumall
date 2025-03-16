package com.wzy.quanyoumall.service;

import com.wzy.quanyoumall.vo.CartItemVo;
import com.wzy.quanyoumall.vo.CartVo;
import com.wzy.quanyoumall.vo.UserInfoVo;

import java.util.concurrent.ExecutionException;

public interface CartService {

    /**
     * 购物车增加商品
     *
     * @param skuId      商品id
     * @param num        商品数量
     * @param userInfoVo 登录用户
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    CartItemVo addCartItem(Long skuId, Integer num, UserInfoVo userInfoVo) throws ExecutionException, InterruptedException, ClassNotFoundException;

    /**
     * 根据用户和商品id查询购物车内商品信息
     *
     * @param skuId      商品id
     * @param userInfoVo 登录用户
     * @return
     */
    CartItemVo getCartItemBySkuId(Long skuId, UserInfoVo userInfoVo);

    CartVo getCartByUser(UserInfoVo userInfoVo);

    void updateUserCartItem(CartItemVo cartItemVo, UserInfoVo userInfoVo);

    void deleteUserCartItem(Long skuId, UserInfoVo userInfoVo);
}
