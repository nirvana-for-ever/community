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
    /**
     * 该评论的父级id
     * 对于问题的评论就是问题的id
     * 对于评论的评论就是上一级评论的id
     */
    private Integer qid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论类型
     * 1表示对于问题的评论
     * 2表示对于评论的评论
     */
    private Integer type;
}
