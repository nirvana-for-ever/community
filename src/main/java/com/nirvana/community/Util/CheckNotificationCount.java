package com.nirvana.community.Util;

import com.nirvana.community.model.User;
import com.nirvana.community.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2020--02--07--10:38
 **/

public class CheckNotificationCount {

    public static void check(HttpServletRequest request, UserService userService){
        User user = (User) request.getSession().getAttribute("user");
        if (null != user) {
            Integer unreadNotificationCount = userService.checkNotification(user);
            user.setUnreadNotificationCount(unreadNotificationCount);
            request.getSession().setAttribute("user",user);
        }

    }

}
