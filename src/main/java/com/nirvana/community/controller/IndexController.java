package com.nirvana.community.controller;

import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--28--10:26
 **/
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        //判断用户是否登录，拿着浏览器传过来的cookie值
        Cookie[] cookies = request.getCookies();

        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null) {
            //查询是哪个用户已经登录,给他添加到session中
            User user = userMapper.selectUserByToken(token);
            if (null != user) {
                request.getSession().setAttribute("user", user);
            }
        }

//----------------------------------------------------------------------------------------------------------------------

        //查询现有的问题传回给前端
        List<Question> questions = questionMapper.selectAllQuestion();

        //展示问题还需要问题的发起人的名字，头像，所以将用户和问题一起传给前端
        List<ShowQuestion> showQuestions = new ArrayList<>();

        if (questions.size() != 0) {
            for (Question question : questions) {
                User user = userMapper.selectUserById(question.getCreator());
                if (user != null) {
                    ShowQuestion showQuestion = new ShowQuestion();
                    //这个工具类的方法作用：将question的属性自动copy到showQuestion上，就不用一个一个set了
                    BeanUtils.copyProperties(question, showQuestion);
                    BeanUtils.copyProperties(user,showQuestion);
                    showQuestions.add(showQuestion);
                }
            }
        }

        //一般都不会为null，所以这边直接不考虑为null的情况
        model.addAttribute("showQuestions", showQuestions);

        return "index";
    }

}
