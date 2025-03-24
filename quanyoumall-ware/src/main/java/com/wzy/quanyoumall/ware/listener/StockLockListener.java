package com.wzy.quanyoumall.ware.listener;

import com.alibaba.fastjson2.TypeReference;
import com.rabbitmq.client.Channel;
import com.wzy.quanyoumall.common.constant.OrderStatusEnum;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskEntity;
import com.wzy.quanyoumall.ware.feign.OrderFeignService;
import com.wzy.quanyoumall.ware.service.WareOrderTaskDetailService;
import com.wzy.quanyoumall.ware.service.WareSkuService;
import com.wzy.quanyoumall.ware.to.OrderTo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RabbitListener(queues = {"stock.release.queue"})
public class StockLockListener {
    @Autowired
    private WareOrderTaskDetailService orderTaskDetailService;
    @Autowired
    private WareSkuService wareSkuService;
    @Autowired
    private OrderFeignService orderFeignService;

    @RabbitHandler
    public void receiveMsg(WareOrderTaskEntity wareOrderTaskEntity, Message message, Channel channel) throws IOException {
        try {
            System.out.println("消费库存消息............");
            R r = orderFeignService.infoBySn(wareOrderTaskEntity.getOrderSn());
            OrderTo order = r.getData(new TypeReference<OrderTo>() {
            });
            if (ObjectUtils.isNotEmpty(order)
                    && (OrderStatusEnum.CANCLED.getCode().equals(order.getStatus()) || OrderStatusEnum.CREATE_NEW.getCode().equals(order.getStatus()))) {
                // 如果订单存在,但状态为已取消/ 超时未支付，释放库存。
                List<WareOrderTaskDetailEntity> taskDetailEntityList = orderTaskDetailService.listByTaskId(wareOrderTaskEntity.getId());
                wareSkuService.rollbackStock(taskDetailEntityList);
            }
            if (ObjectUtils.isEmpty(order)) {
                // 订单不存在,直接释放库存
                List<WareOrderTaskDetailEntity> taskDetailEntityList = orderTaskDetailService.listByTaskId(wareOrderTaskEntity.getId());
                wareSkuService.rollbackStock(taskDetailEntityList);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
