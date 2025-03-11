package com.wzy.quanyoumall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.feign.ProductFeignService;
import com.wzy.quanyoumall.service.CartService;
import com.wzy.quanyoumall.to.SkuInfoTo;
import com.wzy.quanyoumall.vo.CartItemVo;
import com.wzy.quanyoumall.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_PREFIX = "quanyoumall:cart";

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public CartItemVo getCartItemBySkuId(Long skuId, UserInfoVo userInfoVo) {
        Long userId = userInfoVo.getUserId();
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(CART_PREFIX + userId);
        String skuStr = (String) boundHashOps.get(skuId.toString());
        return JSON.parseObject(skuStr, CartItemVo.class);
    }

    @Override
    public CartItemVo addCartItem(Long skuId, Integer num, UserInfoVo userInfoVo) throws ExecutionException, InterruptedException {
        Long userId = userInfoVo.getUserId();
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(CART_PREFIX + userId);
        String skuStr = (String) boundHashOps.get(skuId.toString());
        if (StringUtils.isBlank(skuStr)) {
            CartItemVo cartItemVo = new CartItemVo();
            // 商品信息
            CompletableFuture<Void> skuTask = CompletableFuture.runAsync(() -> {
                R res = productFeignService.info(skuId);
                SkuInfoTo skuInfo = res.getData("skuInfo", new TypeReference<SkuInfoTo>() {
                });
                cartItemVo.setImage(skuInfo.getSkuDefaultImg());
                cartItemVo.setTitle(skuInfo.getSkuTitle());
                cartItemVo.setPrice(skuInfo.getPrice());
                cartItemVo.setSkuId(skuId);
                cartItemVo.setCount(num);
                cartItemVo.setTotalPrice();
            }, threadPoolExecutor);
            // 销售属性
            CompletableFuture<Void> attrListTask = CompletableFuture.runAsync(() -> {
                R res = productFeignService.getSaleAttrListBySkuId(skuId);
                List<String> saleAttrList = res.getData("data", new TypeReference<List<String>>() {
                });
                cartItemVo.setSkuAttrValues(saleAttrList);
            }, threadPoolExecutor);
            CompletableFuture.allOf(skuTask, attrListTask).get();
            // 存入redis
            boundHashOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));
            return cartItemVo;
        }
        CartItemVo itemVo = JSON.parseObject(skuStr, CartItemVo.class);
        if (itemVo != null) {
            itemVo.setCount(itemVo.getCount() + num);
            itemVo.setTotalPrice();
        }
        boundHashOps.put(skuId.toString(), JSON.toJSONString(itemVo));
        return itemVo;
    }
}
