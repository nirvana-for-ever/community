package com.nirvana.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description: 访问access_token获取accesstoken时传输数据对象的封装
 * @author: Nirvana
 * @create: 2019--11--28--14:02
 **/
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
