package com.nirvana.community.dto;

import com.nirvana.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2020--02--06--14:18
 **/
@Data
public class ShowNotification {
    //type表示消息的类型，其值为"回复了您的问题"或"回复了您的评论"
    private String type;
    //发出消息的用户信息
    private User user;
    //问题或者评论
    private String content;
    //问题的id，查看消息的时候如果要查看详情就进入问题查询详情
    private Integer id;
    //时间
    private Long gmtCreate;
}
