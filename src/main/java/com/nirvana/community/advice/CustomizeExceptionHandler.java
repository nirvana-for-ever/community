package com.nirvana.community.advice;

import com.nirvana.community.common.Constants;
import com.nirvana.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: community
 * @description: 处理exception
 * @author: Nirvana
 * @create: 2019--12--19--11:58
 **/
@ControllerAdvice
public class CustomizeExceptionHandler{

    //括号里放异常的类
    @ExceptionHandler(Exception.class)
    //可以加上responsebody注解返回json格式字符串，这里由于没有前后端分离就随意一点
    public ModelAndView handle(Throwable ex, Model model){
        if (ex instanceof CustomizeException) {
            model.addAttribute("message",ex.getMessage());
        }else {
            model.addAttribute("message", Constants.OTHER_EXCEPTION);
        }
        return new ModelAndView("error");
    }
}
