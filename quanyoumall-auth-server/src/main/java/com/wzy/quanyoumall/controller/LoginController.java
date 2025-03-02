package com.wzy.quanyoumall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/login")
public class LoginController {
    @GetMapping({"/", "login.html"})
    public String login(){
        return "login";
    }
}
