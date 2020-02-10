package com.nirvana.community.controller;

import com.nirvana.community.Util.CheckNotificationCount;
import com.nirvana.community.dto.ShowComment;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.User;
import com.nirvana.community.service.CommentService;
import com.nirvana.community.service.QuestionService;
import com.nirvana.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--06--15:46
 **/
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") int id,
                           Model model,
                           HttpServletRequest request){

        //查询问题
        ShowQuestion showQuestion = questionService.queryQuestionById(id,true);
        model.addAttribute("showQuestion",showQuestion);

        //查询相关问题
        List<ShowQuestion> rQuestions = questionService.queryRelatedQuestion(showQuestion.getTag(),showQuestion.getQuestionId());
        model.addAttribute("rQuestions",rQuestions);

        //查询评论
        List<ShowComment> showComments = commentService.queryCommentByQId(id);
        model.addAttribute("showComments",showComments);

        CheckNotificationCount.check(request,userService);

        return "question";
    }

}
