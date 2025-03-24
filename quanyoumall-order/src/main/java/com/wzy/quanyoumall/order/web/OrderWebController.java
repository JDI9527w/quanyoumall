package com.wzy.quanyoumall.order.web;

import com.wzy.quanyoumall.common.exception.BizCodeEnum;
import com.wzy.quanyoumall.order.service.OrderService;
import com.wzy.quanyoumall.order.vo.OrderConfirmVo;
import com.wzy.quanyoumall.order.vo.OrderSubmitVo;
import com.wzy.quanyoumall.order.vo.SubmitOrderRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderWebController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单页
     *
     * @return
     */
    @GetMapping("/list.html")
    public String toListWeb(Model model) {
//        OrderCreateTo orderEntity = new OrderCreateTo();
//        model.addAttribute("order",orderEntity);
//        model.addAttribute("pageUtil",null);
//        return "list";
        return null;
    }

    /**
     * 确认订单页
     *
     * @return
     */
    @GetMapping("/confirm.html")
    public String toConfirmWeb(Model model) {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrder", orderConfirmVo);
        return "confirm";
    }

    /**
     * 下单
     *
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model, RedirectAttributes attributes) {
        SubmitOrderRespVo respVo = orderService.submitOrder(submitVo);
        Integer code = respVo.getCode();
        if (code == 0) {
            model.addAttribute("order", respVo.getOrder());
            return "pay";
        } else {
            String msg = "下单失败;";
            switch (code) {
                case 1:
                    msg += "防重令牌校验失败";
                    break;
                case 2:
                    msg += "商品价格发生变化";
                    break;
                case 16001:
                    msg += BizCodeEnum.OUT_OF_STOCK_EXCEPTION.getMsg();
            }
            attributes.addFlashAttribute("msg", msg);
            return "redirect:http://8.152.219.128/order/confirm.html";
        }
    }
}
