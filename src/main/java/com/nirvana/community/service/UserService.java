package com.nirvana.community.service;

import com.nirvana.community.mapper.NotificationMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Notification;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--07--14:57
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    public User queryUserByName(String name) {
        return userMapper.selectByName(name);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    public int addUser(User user) {

        user.setGmtCreate(System.currentTimeMillis());
        user.setAccountId(UUID.randomUUID().toString());

        return userMapper.insertSelective(user);
    }

    public User checkUser(User pUser) {
        User rUser = userMapper.selectByNameAndPassword(pUser);
        if (null != rUser) {
            //查询未读消息数
            List<Notification> notificationList = notificationMapper.selectByReceiver(rUser.getId());
            long count = notificationList.stream().filter(notification -> notification.getStatus() == 0).count();
            rUser.setUnreadNotificationCount((int) count);
        }
        return rUser;
    }

    public int login(User lUser) {

        lUser.setGmtModified(System.currentTimeMillis());
        lUser.setToken(UUID.randomUUID().toString());
        if (lUser.getPictureUrl()==null){
            lUser.setPictureUrl("/img/default.jpg");
        }

        return userMapper.updateByPrimaryKeySelective(lUser);

    }
}
