package com.nirvana.community.controller;

import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.model.User;
import com.nirvana.community.service.IndexService;
import com.nirvana.community.service.IsLoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IsLoginService isLoginService;

    @Autowired
    private IndexService indexService;

    @GetMapping("/")//                                                       如果没有传值，则默认页码是第一页
    public String index(@RequestParam(value = "destinationPage",required = false,defaultValue = "1") Integer destinationPage,
                        @RequestParam(value = "order",required = false) String order,
                        HttpServletRequest request,
                        Model model) {

        //判断用户是否登录
        isLoginService.isLogin(request);

        //判断一共有几条数据
        Integer questionCount = indexService.queryQuestionCount();
        //每页几条
        Integer each = 5;
        //最后一页
        Integer lastPage = (questionCount/ each)+1;

        Integer index = 0;

        if ("start".equals(order)){
            index = 0;
            destinationPage = 1;
        }else if ("end".equals(order)){
            index = (questionCount / each) * each;
            destinationPage = lastPage;
        }else {
            index = (destinationPage - 1) * each;
        }

        //查询现有的问题传回给前端
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("each", each);
        pageMap.put("index", index);
        List<Question> questions = indexService.queryQuestionByPage(pageMap);

        //由于前端页面展示的样式是（首页，上两页，上一页，当前页，下一页，下两页，尾页），当当前页为第一页时，就变成（首页，当前页，下一页，下两页，下三页，下四页，尾页）
        //所以需要判断当前处在的页面是不是处于前2页，或者是倒数2页
        //@TODO 用前端的分页插件实现分页
        if (destinationPage<3){
            model.addAttribute("one",1);
            model.addAttribute("two",2);
            model.addAttribute("three",3);
            model.addAttribute("four",4);
            model.addAttribute("five",5);
            if (destinationPage==1){
                model.addAttribute("current",1);
            }else if (destinationPage==2){
                model.addAttribute("current",2);
            }
        }else if (destinationPage>lastPage-2){
            model.addAttribute("one",lastPage-4);
            model.addAttribute("two",lastPage-3);
            model.addAttribute("three",lastPage-2);
            model.addAttribute("four",lastPage-1);
            model.addAttribute("five",lastPage);
            if (destinationPage==lastPage-1){
                model.addAttribute("current",lastPage-1);
            }else if (destinationPage==lastPage){
                model.addAttribute("current",lastPage);
            }
        }else {
            model.addAttribute("one",destinationPage-2);
            model.addAttribute("two",destinationPage-1);
            model.addAttribute("three",destinationPage);
            model.addAttribute("four",destinationPage+1);
            model.addAttribute("five",destinationPage+2);
            model.addAttribute("current",destinationPage);
        }
        //展示问题还需要问题的发起人的名字，头像，所以将用户和问题一起传给前端
        List<ShowQuestion> showQuestions = new ArrayList<>();

        if (questions.size() != 0) {
            for (Question question : questions) {
                User user = indexService.queryUserByCreator(question.getCreator());
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
