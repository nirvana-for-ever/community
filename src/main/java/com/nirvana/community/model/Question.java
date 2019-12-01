package com.nirvana.community.model;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--30--21:47
 **/
@Data
public class Question {

    private int id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer readCount;
    private Integer commentCount;
    private Integer likeCount;
    private String tag;

}
