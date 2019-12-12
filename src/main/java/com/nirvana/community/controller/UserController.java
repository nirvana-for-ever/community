package com.nirvana.community.controller;

import com.nirvana.community.model.User;
import com.nirvana.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--07--12:33
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public String toLogin(HttpServletRequest request, Model model){

        //获取浏览器cookie，判断是否有记住账户密码的用户
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    model.addAttribute("name",cookie.getValue());
                }else if (cookie.getName().equals("password")){
                    model.addAttribute("password",cookie.getValue());
                }
            }
        }
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestParam(value = "name")String name,
                        @RequestParam(value = "password")String password,
                        @RequestParam(value = "rem")Boolean rem,
                        HttpServletResponse response,
                        HttpServletRequest request){

        User pUser = new User();
        pUser.setName(name);
        pUser.setPassword(password);

        User lUser = userService.checkUser(pUser);

        if (null != lUser) {
            //用户输入的密码用户名是存在的，给其附上token值和登录时间
            int modifiedCount = userService.login(lUser);
            if (modifiedCount>0){
                User user = userService.queryUserByName(lUser.getName());
                if (null != user) {
                    Cookie cookie = new Cookie("token",user.getToken());
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    request.getSession().setAttribute("user", user);

                    //是否要求记住用户名密码
                    if (rem){
                        Cookie nameCookie = new Cookie("name",name);
                        Cookie passwordCookie = new Cookie("password",password);
                        //保留1年
                        nameCookie.setMaxAge(60*60*24*365);
                        passwordCookie.setMaxAge(60*60*24*365);
                        response.addCookie(nameCookie);
                        response.addCookie(passwordCookie);
                    }

                    return "OK";
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }else {
            return null;
        }

    }


    @GetMapping("/user/register")
    public String toRegister(){
        return "register";
    }

    @PostMapping("user/checkName")
    @ResponseBody
    public String checkName(@RequestParam(value = "name")String name){
        User user = userService.queryUserByName(name);
        if (user!=null){
            return null;
        }else {
            return "OK";
        }
    }

    @PostMapping("/user/register")
    @ResponseBody
    public String register(@RequestParam(value = "name")String name,
                           @RequestParam(value = "password")String password){

        User user = new User();
        user.setName(name);;
        user.setPassword(password);

        int addCount = userService.addUser(user);
        if (addCount>0){
            return "OK";
        }else {
            return null;
        }
    }

    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response){
        //删除session中的user
        request.getSession().removeAttribute("user");
    }

}
