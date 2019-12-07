package com.nirvana.community.controller;

import com.nirvana.community.model.User;
import com.nirvana.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String toLogin(){
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestParam(value = "name")String name,
                        @RequestParam(value = "password")String password,
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
                    response.addCookie(new Cookie("token",user.getToken()));
                    request.getSession().setAttribute("user", user);
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
    public String logout(HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        user.setToken("");
        userService.logout(user);

        request.getSession().removeAttribute("user");

        return "redirect:/";
    }

}
