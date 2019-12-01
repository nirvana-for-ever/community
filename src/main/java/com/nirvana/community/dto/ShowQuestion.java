package com.nirvana.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description: 展示问题需要的元素
 * @author: Nirvana
 * @create: 2019--12--01--16:45
 **/
@Data
public class ShowQuestion {

    private String pictureUrl;
    private String name;
    private Integer commentCount;
    private Integer readCount;
    private String title;
    private Long gmtCreate;

}
