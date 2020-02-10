package com.nirvana.community.controller;

import com.nirvana.community.Util.CheckNotificationCount;
import com.nirvana.community.Util.PaginationUtil;
import com.nirvana.community.dto.ShowNotification;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.model.Notification;
import com.nirvana.community.model.User;
import com.nirvana.community.service.IndexService;
import com.nirvana.community.service.ProfileService;
import com.nirvana.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: community
 * @description: 个人页面
 * @author: Nirvana
 * @create: 2019--12--04--13:16
 **/
@Controller
public class ProfileController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    //用/方式接参数
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          @RequestParam(value = "destinationPage",required = false,defaultValue = "1") Integer destinationPage,
                          @RequestParam(value = "order",required = false) String order,
                          HttpServletRequest request,
                          Model model){

        CheckNotificationCount.check(request,userService);

        //如果路径的值是questions就跳转到我的问题页面
        if("questions".equals(action)){
            model.addAttribute("title","我的问题");
            model.addAttribute("action","questions");

            //封装分页查询需要的参数
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("order",order);
            paramMap.put("destinationPage",destinationPage);
            User user = (User) request.getSession().getAttribute("user");
            paramMap.put("id",user.getId());

            //查找出我的问题，并分页,少于20才分页
            List<ShowQuestion> showQuestions = PaginationUtil.getShowQuestions(paramMap,indexService,model);

            model.addAttribute("showQuestions",showQuestions);

        }else if (StringUtils.equals(action,"notifications")){
            //路径是notifications是要查看消息
            model.addAttribute("title","我的消息");
            model.addAttribute("action","notifications");

            //不分页了，反正有插件
            User user = (User) request.getSession().getAttribute("user");
            List<ShowNotification> notifications = profileService.queryNotificationByUser(user);

            //同步修改session中的user的未读数
            user.setUnreadNotificationCount(0);
            request.getSession().setAttribute("user",user);

            model.addAttribute("notifications",notifications);

        }
        return "profile";
    }

}
