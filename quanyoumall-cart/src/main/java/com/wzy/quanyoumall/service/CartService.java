package com.wzy.quanyoumall.service;

import com.wzy.quanyoumall.authServer.vo.CartItemVo;
import com.wzy.quanyoumall.authServer.vo.CartVo;
import com.wzy.quanyoumall.authServer.vo.UserInfoVo;

import java.util.List;
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

    /**
     * 通过用户获取用户购物车全部信息
     *
     * @param userInfoVo
     * @return
     */
    CartVo getCartByUser(UserInfoVo userInfoVo);

    /**
     * 更新用户购物车信息
     *
     * @param cartItemVo
     * @param userInfoVo
     */
    void updateUserCartItem(CartItemVo cartItemVo, UserInfoVo userInfoVo);

    /**
     * 删除用户购物车信息
     *
     * @param skuId
     * @param userInfoVo
     */
    void deleteUserCartItem(Long skuId, UserInfoVo userInfoVo);

    /**
     * 获取用户购物车的选中项
     *
     * @param memberId
     * @return
     */
    List<CartItemVo> listGetCheckedItems(Long memberId);

    /**
     * 通过用户id获取用户购物车项list
     *
     * @param memberId
     * @return
     */
    List<CartItemVo> listGetUserCartItem(Long memberId);
}
