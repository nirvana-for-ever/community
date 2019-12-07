package com.nirvana.community.model;

import lombok.Data;

/**
 * @program: community
 * @description: 用户
 * @author: Nirvana
 * @create: 2019--11--29--13:00
 **/
@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String pictureUrl;
    private String bio;
    private String password;

}
