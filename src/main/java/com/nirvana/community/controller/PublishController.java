package com.nirvana.community.controller;

import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import com.nirvana.community.service.IsLoginService;
import com.nirvana.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 发起问题
 * @author: Nirvana
 * @create: 2019--11--30--15:34
 **/
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PublishService publishService;

    @Autowired
    private IsLoginService isLoginService;

    //当get访问时，跳转到发布问题的页面
    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request){

        isLoginService.isLogin(request);
        return "publish";

    }

    //当以post方式访问的时候，做提交问题，两种路径一样但方式不同
    @PostMapping("/publish")
    public void publish(@RequestParam(value = "title") String title,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "tag") String tag,
                          HttpServletRequest request){

        //@TODO 做一个拦截器，判断用户是否登录，有登录才能发布问题
        isLoginService.isLogin(request);
        User user = (User) request.getSession().getAttribute("user");

        Question question = new Question();
        question.setCreator(user.getId());
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        int addCount = publishService.addQuestion(question);

        //@TODO 添加错误响应的方式
//        if (addCpunt>0){
//
//        }

    }

}
