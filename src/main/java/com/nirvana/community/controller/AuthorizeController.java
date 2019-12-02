package com.nirvana.community.controller;

import com.nirvana.community.Util.GithubUtil;
import com.nirvana.community.dto.AccessTokenDTO;
import com.nirvana.community.dto.GithubUser;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import com.nirvana.community.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @program: community
 * @description: 登录时通过github认证
 * @author: Nirvana
 * @create: 2019--11--28--12:38
 **/

@Controller
public class AuthorizeController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String state,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        //这两个都是注册OAuth App生成的
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);

        User user = authorizeService.getToken(accessTokenDTO);

        if (null != user) {
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else {
            //登录失败@TODO
            return "redirect:/";
        }
    }



}
