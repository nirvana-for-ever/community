package com.nirvana.community.dto;

import com.nirvana.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2020--01--28--20:50
 **/
@Data
public class ShowComment {

    private Integer id;
    private Integer qId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private String content;
    private Integer likeCount;
    private User user;

}
