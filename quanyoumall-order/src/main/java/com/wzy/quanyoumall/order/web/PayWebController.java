package com.wzy.quanyoumall.order.web;

import com.alipay.api.AlipayApiException;
import com.wzy.quanyoumall.order.config.AlipayTemplate;
import com.wzy.quanyoumall.order.service.OrderService;
import com.wzy.quanyoumall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order/pay")
public class PayWebController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayTemplate alipayTemplate;

    @ResponseBody
    @GetMapping(value = "/aliPayOrder", produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderService.aliPayOrder(orderSn);
        String pay = alipayTemplate.pay(payVo);
        return pay;
    }
}
