package com.wzy.quanyoumall.controller;

import com.alibaba.fastjson2.TypeReference;
import com.wzy.quanyoumall.common.constant.AuthServerConstant;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.common.vo.MemberRespVo;
import com.wzy.quanyoumall.feign.MemberFeignService;
import com.wzy.quanyoumall.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    private MemberFeignService memberFeignService;

    @GetMapping({"/", "login.html"})
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(UserLoginVo userLoginVo, RedirectAttributes attributes, HttpSession session) {
        R r = memberFeignService.login(userLoginVo);
        Integer code = r.getCode();
        if (code != 0) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("msg", r.get("msg").toString());
            attributes.addFlashAttribute("errors", errors);
            return "redirect://8.152.219.128/auth/login/login.html";
        }
        MemberRespVo memberRespVo = r.getData("data", new TypeReference<MemberRespVo>() {
        });
        session.setAttribute(AuthServerConstant.LOGIN_USER, memberRespVo);
        return "redirect://8.152.219.128/product/index.html";
    }
}
