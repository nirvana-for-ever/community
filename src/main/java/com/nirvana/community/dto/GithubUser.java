package com.nirvana.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--28--18:11
 **/
@Data
public class GithubUser {

    private String id;
    private String name;
    private String bio;
    private String avatar_url;

}
