package com.nirvana.community.service;

import com.nirvana.community.common.Constants;
import com.nirvana.community.dto.ShowNotification;
import com.nirvana.community.mapper.CommentMapper;
import com.nirvana.community.mapper.NotificationMapper;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Comment;
import com.nirvana.community.model.Notification;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--04--23:19
 **/
@Service
public class ProfileService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    public List<ShowNotification> queryNotificationByUser(User user) {

        //查询该用户所有的消息list
        List<Notification> notificationList = notificationMapper.selectByReceiver(user.getId());

        List<ShowNotification> showNotifications = notificationList.stream().map(notification -> {
            if (notification.getType().equals(Constants.TO_QUESTION)) {
                //对问题的消息
                ShowNotification showNotification = new ShowNotification();
                showNotification.setUser(userMapper.selectByPrimaryKey(notification.getSender()));
                Question question = questionMapper.selectByPrimaryKey(notification.getQid());
                showNotification.setContent(question.getTitle());
                showNotification.setId(question.getId());
                showNotification.setType(Constants.TO_QUESTION_STRING);
                showNotification.setGmtCreate(notification.getGmtCreate());
                return showNotification;
            } else {
                //对于评论的消息
                ShowNotification showNotification = new ShowNotification();
                showNotification.setUser(userMapper.selectByPrimaryKey(notification.getSender()));

                Comment comment = commentMapper.selectByPrimaryKey(notification.getQid());
                Question question = new Question();
                if (comment.getType().equals(Constants.TO_QUESTION)) {
                    question = questionMapper.selectByPrimaryKey(comment.getqId());
                } else {
                    question = questionMapper.selectByPrimaryKey(commentMapper.selectByPrimaryKey(comment.getqId()).getqId());
                }

                showNotification.setContent(comment.getContent());
                showNotification.setId(question.getId());
                showNotification.setType(Constants.TO_COMMENT_STRING);
                showNotification.setGmtCreate(notification.getGmtCreate());
                return showNotification;
            }
        }).collect(Collectors.toList());

        //将该用户的消息都设成已读
        Notification notification = new Notification();
        notification.setStatus(Constants.READ);
        notification.setReceiver(user.getId());
        notificationMapper.updateStatusByReceiver(notification);

        return showNotifications;
    }
}
