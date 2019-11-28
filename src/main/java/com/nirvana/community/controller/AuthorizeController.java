package com.nirvana.community.controller;

import com.nirvana.community.Util.GithubUtil;
import com.nirvana.community.dto.AccessTokenDTO;
import com.nirvana.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 登录时通过github认证
 * @author: Nirvana
 * @create: 2019--11--28--12:38
 **/

@Controller
public class AuthorizeController {

    @Autowired
    private GithubUtil githubUtil;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        //这两个都是注册OAuth App生成的
        accessTokenDTO.setClient_Id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = githubUtil.getAccessToken(accessTokenDTO);

        GithubUser githubUser = githubUtil.getUser(accessToken);

        if (null != githubUser) {
            //登录成功后将用户存放到session域中
            request.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }else {
            //登录失败@TODO
            return "redirect:/";
        }
    }



}
