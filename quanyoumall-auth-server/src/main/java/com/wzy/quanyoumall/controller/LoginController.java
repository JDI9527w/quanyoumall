package com.wzy.quanyoumall.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.feign.MemberFeignService;
import com.wzy.quanyoumall.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String userLogin(UserLoginVo userLoginVo, Model model) {
        R r = memberFeignService.checkMember(userLoginVo);
        Integer code = r.getCode();
        if (code != 0) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("msg", r.get("msg").toString());
            model.addAttribute("errors", errors);
            return "login";
        }
        return "redirect://8.152.219.128/product/index.html";
    }
}
