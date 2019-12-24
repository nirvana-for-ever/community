package com.nirvana.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--24--12:39
 **/
@Data
public class ShowComment {
    private Integer qid;
    private String content;
    private Integer type;
}
