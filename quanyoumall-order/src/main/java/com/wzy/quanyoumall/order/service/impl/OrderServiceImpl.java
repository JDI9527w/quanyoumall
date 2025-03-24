package com.wzy.quanyoumall.order.service.impl;

import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.exception.BizCodeEnum;
import com.wzy.quanyoumall.common.constant.OrderConstant;
import com.wzy.quanyoumall.common.constant.OrderStatusEnum;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.common.vo.MemberRespVo;
import com.wzy.quanyoumall.order.entity.OrderEntity;
import com.wzy.quanyoumall.order.entity.OrderItemEntity;
import com.wzy.quanyoumall.order.feign.CartFeignService;
import com.wzy.quanyoumall.order.feign.MemberFeignService;
import com.wzy.quanyoumall.order.feign.ProductFeignService;
import com.wzy.quanyoumall.order.feign.WareFeignService;
import com.wzy.quanyoumall.order.interceptor.LoginUserInterceptor;
import com.wzy.quanyoumall.order.mapper.OrderMapper;
import com.wzy.quanyoumall.order.service.OrderItemService;
import com.wzy.quanyoumall.order.service.OrderService;
import com.wzy.quanyoumall.order.to.OrderCreateTo;
import com.wzy.quanyoumall.order.to.SpuInfoTo;
import com.wzy.quanyoumall.order.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public OrderConfirmVo confirmOrder() {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        List<MemberAddressVo> memberAddressVos = memberFeignService.listGetMemberReceiveAddr(memberRespVo.getId());
        orderConfirmVo.setMemberAddressVos(memberAddressVos);

        List<OrderItemVo> orderItemVos = cartFeignService.listGetCheckedItems(memberRespVo.getId());
        orderConfirmVo.setItems(orderItemVos);

        Map<Long, Boolean> stocks = new HashMap<>();
        // TODO:懒得查库存了,默认所有加购物车的都有库存.
        for (OrderItemVo orderItemVo : orderItemVos) {
            stocks.put(orderItemVo.getSkuId(), true);
        }
        orderConfirmVo.setStocks(stocks);
        orderConfirmVo.setIntegration(memberRespVo.getIntegration());
        String orderToken = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), orderToken, 30, TimeUnit.MINUTES);
        orderConfirmVo.setOrderToken(orderToken);
        return orderConfirmVo;
    }


    @Override
//    @GlobalTransactional
    @Transactional
    public SubmitOrderRespVo submitOrder(OrderSubmitVo submitVo) {
        SubmitOrderRespVo respVo = new SubmitOrderRespVo();
        // 构建订单
        OrderCreateTo orderCreateTo = createOrder(submitVo);
        // 验价
        BigDecimal payPriceCalc = orderCreateTo.getPayPrice();
        BigDecimal payPriceParam = submitVo.getPayPrice();
        if (Math.abs(payPriceCalc.subtract(payPriceParam).doubleValue()) < 0.01) {
            // 保存订单信息
            this.saveOrder(orderCreateTo);
            List<OrderItemVo> itemvos = orderCreateTo.getOrderItems().stream().map(item -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                orderItemVo.setSkuId(item.getSkuId());
                orderItemVo.setCount(item.getSkuQuantity());
                return orderItemVo;
            }).collect(Collectors.toList());
            // 锁定库存
            R lockRes = wareFeignService.lockStockByLockVo(new WareSkuLockVo(orderCreateTo.getOrder().getOrderSn(), itemvos));
            if (lockRes.getCode() == 0) {
                // 锁成功
                // 发送订单信息
                rabbitTemplate.convertAndSend("order.event.exchange", "order.create.order", orderCreateTo.getOrder());
                respVo.setCode(0);
                respVo.setOrder(orderCreateTo.getOrder());
                return respVo;
            } else {
//                锁失败,回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                respVo.setCode(BizCodeEnum.OUT_OF_STOCK_EXCEPTION.getCode());
                return respVo;
            }
        }
        // 价格变化.
        respVo.setCode(2);
        return respVo;
    }

    private void saveOrder(OrderCreateTo orderCreateTo) {
        this.save(orderCreateTo.getOrder());
        orderItemService.saveBatch(orderCreateTo.getOrderItems());
    }

    /**
     * 创建订单
     *
     * @param submitVo
     * @return
     */
    private OrderCreateTo createOrder(OrderSubmitVo submitVo) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        OrderEntity orderEntity = new OrderEntity();
        String orderSn = IdWorker.getIdStr();
        // sn码
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberId(memberRespVo.getId());
        orderEntity.setMemberUsername(memberRespVo.getUsername());
        // 状态
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        // 收货信息
        this.buildOrderReceive(submitVo.getAddrId(), orderEntity);
        // 运费信息
        this.buildOrderFare(submitVo.getAddrId(), orderEntity);
        // 构建订单项
        List<OrderItemEntity> orderItemEntityList = this.buildOrderItems(orderSn);
        // 计算各种金额
        this.calculateAmount(orderItemEntityList, orderEntity);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(orderItemEntityList);
        orderCreateTo.setFare(orderEntity.getFreightAmount());
        orderCreateTo.setPayPrice(orderEntity.getPayAmount());
        return orderCreateTo;
    }

    /**
     * 构建订单项
     *
     * @param orderSn
     * @return
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        // 用户购物车中选中的商品
        List<OrderItemVo> orderItemVos = cartFeignService.listGetCheckedItems(memberRespVo.getId());
        List<OrderItemEntity> orderItemList = new ArrayList<>();
        for (OrderItemVo orderItemVo : orderItemVos) {
            OrderItemEntity oie = new OrderItemEntity();
            R spuRes = productFeignService.getSpuInfoBySkuId(orderItemVo.getSkuId());
            if (spuRes.getCode() == 0) {
                SpuInfoTo spuInfo = spuRes.getData("data", new TypeReference<SpuInfoTo>() {
                });
                oie.setSpuId(spuInfo.getId());
                oie.setSpuBrand(spuInfo.getBrandId().toString());
                oie.setSpuName(spuInfo.getSpuName());
                oie.setSpuPic(spuInfo.getSpuDescription());
                oie.setCategoryId(spuInfo.getCatalogId());
            }
            oie.setOrderSn(orderSn);
            oie.setSkuId(orderItemVo.getSkuId());
            oie.setSkuName(orderItemVo.getTitle());
            oie.setSkuPic(orderItemVo.getImage());
            oie.setSkuPrice(orderItemVo.getPrice());
            oie.setSkuQuantity(orderItemVo.getCount());
            String skuAttrVals = StringUtils.collectionToDelimitedString(orderItemVo.getSkuAttrValues(), ";");
            oie.setSkuAttrsVals(skuAttrVals);
            oie.setPromotionAmount(BigDecimal.ZERO);
            oie.setCouponAmount(BigDecimal.ZERO);
            oie.setIntegrationAmount(BigDecimal.ZERO);
            // 原总价
            BigDecimal originalPrice = orderItemVo.getPrice().multiply(new BigDecimal(orderItemVo.getCount()));
            // 减去优惠后的实际价格
            BigDecimal realPrice = originalPrice.subtract(oie.getPromotionAmount())
                    .subtract(oie.getCouponAmount())
                    .subtract(oie.getIntegrationAmount());
            oie.setRealAmount(realPrice);
            oie.setGiftGrowth(realPrice.intValue());
            oie.setGiftIntegration(realPrice.intValue());
            orderItemList.add(oie);
        }
        return orderItemList;
    }

    /**
     * 构建收货信息
     */
    private void buildOrderReceive(Long addrId, OrderEntity orderEntity) {
        R info = memberFeignService.info(addrId);
        if (info.getCode() == 0) {
            MemberAddressVo address = info.getData("data", new TypeReference<MemberAddressVo>() {
            });
            orderEntity.setReceiverName(address.getName());
            orderEntity.setReceiverPhone(address.getPhone());
            orderEntity.setReceiverCity(address.getCity());
            orderEntity.setReceiverDetailAddress(address.getDetailAddress());
            orderEntity.setReceiverProvince(address.getProvince());
            orderEntity.setReceiverRegion(address.getRegion());
            orderEntity.setReceiverPostCode(address.getPostCode());
        }
    }

    /**
     * 构建运费信息:假设商品整单发货,购物项没有单独运费信息
     *
     * @param addrId
     * @param orderEntity
     */
    private void buildOrderFare(Long addrId, OrderEntity orderEntity) {
        FareVo fareVo = wareFeignService.getFareByAddrId(addrId);
        orderEntity.setFreightAmount(fareVo.getFare());
    }

    /**
     * 计算金额
     *
     * @param orderItemEntityList
     * @param orderEntity
     */
    private void calculateAmount(List<OrderItemEntity> orderItemEntityList, OrderEntity orderEntity) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal couponAmount = BigDecimal.ZERO;
        for (OrderItemEntity orderItemEntity : orderItemEntityList) {
            totalAmount = totalAmount.add(orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity())));
            couponAmount = couponAmount.add(orderItemEntity.getCouponAmount());
        }
        BigDecimal payAmount = totalAmount.add(orderEntity.getFreightAmount());
        orderEntity.setTotalAmount(totalAmount);
        orderEntity.setPayAmount(payAmount);
        // 如果有活动 如满减, 根据参加活动商品计算金额,对比满减活动设置满减金额,并给购物项均摊
        orderEntity.setPromotionAmount(BigDecimal.ZERO);
        // 如果使用积分设置金额,并把分解金额设置给订单项均摊
        orderEntity.setIntegrationAmount(BigDecimal.ZERO);
        // 如果使用优惠券 (商品优惠券 & 支付优惠券)
        // 如果使用支付优惠券,应该给购物项设置分摊减去的金额 = 优惠券额度*购物项金额占总金额的比例
        orderEntity.setCouponAmount(couponAmount);
        orderEntity.setDiscountAmount(BigDecimal.ZERO);
    }

    @Override
    public void closeOrder(OrderEntity orderEntity) {
        OrderEntity byId = this.getById(orderEntity.getId());
        if (byId != null && byId.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            // 30分钟后接收到消息,如果订单状态为未支付,则取消订单.
            byId.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(byId);
            // TODO:返还优惠券等
        }
    }

    @Override
    public OrderEntity getByOrderSn(String orderSn) {
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        return orderEntity;
    }
}