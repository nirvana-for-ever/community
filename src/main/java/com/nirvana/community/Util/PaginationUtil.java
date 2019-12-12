package com.nirvana.community.Util;

import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import com.nirvana.community.service.IndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--04--23:01
 **/

public class PaginationUtil {
    public static List<ShowQuestion> getShowQuestions(Map<String,Object> paramMap,IndexService indexService,Model model) {

        //分页查询传递参数
        Map<String,Object> pageMap = new HashMap<>();

        //判断一共有几条数据
        Integer questionCount = indexService.queryQuestionCount();

        //判断是否有其它分页查询条件
        if (paramMap.get("id")!=null){
            pageMap.put("creator",paramMap.get("id"));
            questionCount = indexService.queryQuestionCount((Integer) paramMap.get("id"));
        }else {
            //如果没有creator，则统一设置为0
            pageMap.put("creator",0);
        }

        //每页几条
        Integer each = 5;
        //最后一页
        int lastPage = questionCount%each==0 ? questionCount/each : (questionCount/each)+1;
        //order值
        String order = (String) paramMap.get("order");
        //destinationPage
        Integer destinationPage = (Integer) paramMap.get("destinationPage");

        int index = 0;

        if ("start".equals(order)){
            destinationPage = 1;
        }else if ("end".equals(order)){
            index = (lastPage-1) * each;
            destinationPage = lastPage;
        }else {
            index = (destinationPage - 1) * each;
        }

        //准备参数
        pageMap.put("each", each);
        pageMap.put("index", index);

        //查询现有的问题传回给前端
        List<Question> questions = indexService.queryQuestionByPage(pageMap);

        //由于前端页面展示的样式是（首页，上两页，上一页，当前页，下一页，下两页，尾页），当当前页为第一页时，就变成（首页，当前页，下一页，下两页，下三页，下四页，尾页）
        //所以需要判断当前处在的页面是不是处于前2页，或者是倒数2页
        //@TODO 用前端的分页插件实现分页
        model.addAttribute("current",destinationPage);
        if (destinationPage<4){
            model.addAttribute("showStart",false);
            if (lastPage<6){
                model.addAttribute("showEnd",false);
            }else {
                model.addAttribute("showEnd",true);
            }
            Integer[] pages = new Integer[5];
            if (lastPage>4){
                for(int i = 0;i<=4;i++){
                    pages[i] = i+1;
                }
            }else {
                for(int i = 0;i<=lastPage-1;i++){
                    pages[i] = i+1;
                }
            }
            model.addAttribute("pages",pages);
        }else if (destinationPage>lastPage-3){
            Integer[] pages = {lastPage-4,lastPage-3,lastPage-2,lastPage-1,lastPage};
            model.addAttribute("pages",pages);
            model.addAttribute("showEnd",false);
            model.addAttribute("showStart",true);
        }else {
            Integer[] pages = {destinationPage-2,destinationPage-1,destinationPage,destinationPage+1,destinationPage+2};
            model.addAttribute("showStart",true);
            model.addAttribute("showEnd",true);
            model.addAttribute("pages",pages);
        }
        //展示问题还需要问题的发起人的名字，头像，所以将用户和问题一起传给前端
        List<ShowQuestion> showQuestions = new ArrayList<>();

        if (questions.size() != 0) {
            for (Question question : questions) {
                User user = indexService.queryUserByCreator(question.getCreator());
                if (user != null) {
                    ShowQuestion showQuestion = new ShowQuestion();
                    //这个工具类的方法作用：将question的属性自动copy到showQuestion上，就不用一个一个set了
                    BeanUtils.copyProperties(user,showQuestion);
                    BeanUtils.copyProperties(question, showQuestion);
                    showQuestion.setQuestionId(question.getId());
                    showQuestions.add(showQuestion);
                }
            }
        }

        return showQuestions;
    }
}
