package com.nirvana.community.service;

import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 判断是否登录
 * @author: Nirvana
 * @create: 2019--12--02--12:37
 **/
@Service
public class IsLoginService {

    @Autowired
    private UserMapper userMapper;

    public Boolean isLogin(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            //查询是哪个用户已经登录,给他添加到session中
            User user = userMapper.selectUserByToken(token);
            if (null != user) {
                request.getSession().setAttribute("user", user);
            }
            return true;
        }else {
            return false;
        }
    }

}
