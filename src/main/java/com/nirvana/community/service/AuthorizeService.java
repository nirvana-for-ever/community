package com.nirvana.community.service;

import com.nirvana.community.Util.GithubUtil;
import com.nirvana.community.dto.AccessTokenDTO;
import com.nirvana.community.dto.GithubUser;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--02--15:03
 **/
@Service
public class AuthorizeService {

    @Autowired
    private UserMapper userMapper;

    public User getToken(AccessTokenDTO accessTokenDTO) {

        String accessToken = GithubUtil.getAccessToken(accessTokenDTO);

        GithubUser githubUser = GithubUtil.getUser(accessToken);

        if (null != githubUser) {

            //将用户存到数据库中
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());
            //随机创建一个值,后面用作cookie的值，实现判断用户是否已经登录
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setPictureUrl(githubUser.getAvatar_url());

            int insertUserNo = userMapper.insertSelective(user);

            if (insertUserNo > 0){
                //添加用户成功后，将用户的token添加到cookie中
                return user;
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
