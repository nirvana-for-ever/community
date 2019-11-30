package com.nirvana.community.controller;

import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--28--10:26
 **/
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){

        //判断用户是否登录，拿着浏览器传过来的cookie值
        Cookie[] cookies = request.getCookies();

        String token = null;

        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null){
            //查询是哪个用户已经登录,给他添加到session中
            User user = userMapper.selectUserByToken(token);
            if (null != user) {
                request.getSession().setAttribute("user",user);
            }
        }

        return "index";
    }

}
