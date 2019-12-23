package com.nirvana.community.controller;

import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import com.nirvana.community.service.PublishService;
import com.nirvana.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private QuestionService questionService;

    //当get访问时，跳转到发布问题的页面
    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request){
        return "publish";
    }

    //当带有问题的id参数的时候，说明是要进行编辑的操作
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(value = "id")Integer id,
                       Model model){

        ShowQuestion showQuestion = questionService.queryQuestionById(id,false);
        model.addAttribute("id",showQuestion.getQuestionId());
        model.addAttribute("title",showQuestion.getTitle());
        model.addAttribute("description",showQuestion.getDescription());
        model.addAttribute("tag",showQuestion.getTag());

        return "publish";
    }

    //当以post方式访问的时候，做提交问题，两种路径一样但方式不同
    @PostMapping("/publish")
    @ResponseBody
    public String publish(@RequestParam(value = "title") String title,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "tag") String tag,
                          @RequestParam(value = "id")Integer id,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        Question question = new Question();
        //判断传过来的id是否是空的，是空的则说明是新的问题
        if (id != null){
            question.setId(id);
            question.setGmtModified(System.currentTimeMillis());
        }else {
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
        }
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        int addCount = publishService.addOrModifiedQuestion(question);

        if (addCount>0){
            return "OK";
        }else {
            return null;
        }

    }

}
