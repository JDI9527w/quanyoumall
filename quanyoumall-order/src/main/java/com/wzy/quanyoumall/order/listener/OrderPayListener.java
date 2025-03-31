package com.wzy.quanyoumall.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wzy.quanyoumall.order.config.AlipayTemplate;
import com.wzy.quanyoumall.order.service.OrderService;
import com.wzy.quanyoumall.order.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("order/listener")
public class OrderPayListener {

    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private OrderService orderService;

    @PostMapping("/payed/notify")
    public String handleAlipayNotify(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException {
        //处理支付宝回调,验签,修改订单状态.
        System.out.println("支付宝回调支付成功");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type());
        if (signVerified) {
            System.out.println("签名验证成功");
            return orderService.handlerPayResult(payAsyncVo);
        }
        System.out.println("签名验证失败");
        return "error";
    }
}
