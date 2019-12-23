package com.nirvana.community.controller;

import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") int id,
                           Model model){

        ShowQuestion showQuestion = questionService.queryQuestionById(id,true);

        //判断查询回来的问题是否为空，如果不为空，则说明有找到该问题，将该问题的浏览量加1
        if (null != showQuestion) {

        }

        model.addAttribute("showQuestion",showQuestion);

        return "question";
    }

}
