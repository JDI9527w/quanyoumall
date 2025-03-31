package com.wzy.quanyoumall.order.interceptor;

import com.wzy.quanyoumall.common.constant.AuthServerConstant;
import com.wzy.quanyoumall.common.vo.MemberRespVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        boolean match1 = matcher.match("/order/order/info/**", requestURI);
        boolean match2 = matcher.match("/order/pay/**", requestURI);
        boolean match3 = matcher.match("/order/listener/payed/notify", requestURI);
        if (match1 || match2 || match3) return true;

        MemberRespVo member = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (ObjectUtils.isNotEmpty(member)) {
            loginUser.set(member);
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } else {
            request.getSession().setAttribute("msg", "请先登录");
            response.sendRedirect("http://8.152.219.128/auth/login/login.html");
            return false;
        }
    }
}
