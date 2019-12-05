package com.nirvana.community.controller;

import com.nirvana.community.Util.PaginationUtil;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    private IndexService indexService;

    @GetMapping("/")//                                                       如果没有传值，则默认页码是第一页
    public String index(@RequestParam(value = "destinationPage",required = false,defaultValue = "1") Integer destinationPage,
                        @RequestParam(value = "order",required = false) String order,
                        HttpServletRequest request,
                        Model model) {

        //封装分页查询需要的参数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("order",order);
        paramMap.put("destinationPage",destinationPage);

        //调用分页查询工具类
        List<ShowQuestion> showQuestions = PaginationUtil.getShowQuestions(paramMap,indexService,model);

        //一般都不会为null，所以这边直接不考虑为null的情况
        model.addAttribute("showQuestions", showQuestions);

        return "index";
    }

}
