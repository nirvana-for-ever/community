package com.nirvana.community.interceptor;

import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: community
 * @description: 登录拦截器
 * @author: Nirvana
 * @create: 2019--12--05--13:38
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("user");

        if (null == user) {
            //如果没登录，跳转到登录页面
            response.sendRedirect("/user/login");
            return false;
        }else {
            return true;
        }
    }

    //@TODO 如果没登录跳转到登录页
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
