package com.lst.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //这个拦截器会拦截用户的任何请求
        if(request.getSession().getAttribute("user") == null){ //如果还没有登录，即没有再数据库中查到一个user，则重定向到登录页面
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
