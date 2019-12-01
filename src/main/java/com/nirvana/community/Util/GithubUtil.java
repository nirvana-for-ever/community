package com.nirvana.community.Util;

import com.alibaba.fastjson.JSON;
import com.nirvana.community.dto.AccessTokenDTO;
import com.nirvana.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: community
 * @description: 利用github登录的工具util
 * @author: Nirvana
 * @create: 2019--11--28--13:51
 **/

@Component
public class GithubUtil {

    //使用okhttp的post请求
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();

        try (Response response = client.newCall(request).execute()) {

            String returnString = response.body().string();
            String[] split = returnString.split("=")[1].split("&");
            return split[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //get请求
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accessToken).build();

        try {
            Response response = client.newCall(request).execute();
            //返回的是用户的信息的json格式字符串
            String jsonUser = response.body().string();
            return JSON.parseObject(jsonUser, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
