package com.wzy.quanyoumall.authServer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.wzy.quanyoumall.authServer.feign.MemberFeignService;
import com.wzy.quanyoumall.authServer.vo.GiteeUserResp;
import com.wzy.quanyoumall.authServer.vo.GiteeUserVo;
import com.wzy.quanyoumall.common.constant.AuthServerConstant;
import com.wzy.quanyoumall.common.utils.HttpUtils;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.common.vo.MemberRespVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class OAuth2Controller {

    @Autowired
    private MemberFeignService memberFeignService;

    @GetMapping("/OAuth2/gitee/success")
    public String giteeLogin(@RequestParam("code") String code, HttpSession session) {
        String host = "https://gitee.com";
        String path = "/oauth/token";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("grant_type", "authorization_code");
        bodys.put("code", code);
        bodys.put("client_id", "158efeccf946cc88a2de9cd55c353f1c42cc465f726b292304d29b434ec49ab3");
        bodys.put("redirect_uri", "http://8.152.219.128/auth/OAuth2/gitee/success");
        bodys.put("client_secret", "ba6aada97a8d87f5dc23f1b3ff217978a828efeccbe711ee5765e0a37ad43b1d");
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonStr = EntityUtils.toString(response.getEntity());
                GiteeUserResp giteeUserResp = JSON.parseObject(jsonStr, GiteeUserResp.class);
                querys.put("access_token", giteeUserResp.getAccessToken());
                HttpResponse getUserResp = HttpUtils.doGet(host, "/api/v5/user", "GET", headers, querys);
                if (getUserResp.getStatusLine().getStatusCode() == 200) {
                    String giteeUserStr = EntityUtils.toString(getUserResp.getEntity());
                    GiteeUserVo giteeUserVo = JSON.parseObject(giteeUserStr, GiteeUserVo.class);
                    R r = memberFeignService.loginBySocialAccount(giteeUserVo);
                    MemberRespVo memberRespVo = r.getData("data", new TypeReference<MemberRespVo>() {
                    });
                    //TODO:集成security后生成JWT存入redis,并返回前端.
                    session.setAttribute(AuthServerConstant.LOGIN_USER, memberRespVo);
                    return "redirect://8.152.219.128/product/index.html";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect://8.152.219.128/auth/login/login.html";
    }
}
