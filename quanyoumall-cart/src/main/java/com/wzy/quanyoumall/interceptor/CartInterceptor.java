package com.wzy.quanyoumall.interceptor;

import com.wzy.quanyoumall.authServer.vo.UserInfoVo;
import com.wzy.quanyoumall.common.constant.AuthServerConstant;
import com.wzy.quanyoumall.common.vo.MemberRespVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoVo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoVo userInfoVo = new UserInfoVo();
        MemberRespVo member = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (ObjectUtils.isNotEmpty(member)) {
            userInfoVo.setUserId(member.getId());
            threadLocal.set(userInfoVo);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
