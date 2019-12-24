package com.nirvana.community.advice;

import com.alibaba.fastjson.JSON;
import com.nirvana.community.common.Constants;
import com.nirvana.community.dto.ResultDTO;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: community
 * @description: 处理所有的exception
 * @author: Nirvana
 * @create: 2019--12--19--11:58
 **/
@ControllerAdvice
public class CustomizeExceptionHandler{

    //括号里放异常的类
    @ExceptionHandler(Exception.class)
    //可以加上responsebody注解返回json格式字符串，这里由于没有前后端分离就随意一点
    public ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response){

        //判断是不是通过json传递数据的局部刷新，如果是就不进行跳转页面而是仅仅返回json的数据
        if ("application/json".equals(request.getContentType())){
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.getResult(((CustomizeException) ex).getCode(), ex.getMessage());
            }else {
                resultDTO = ResultDTO.getResult(CustomizeErrorCode.INNER_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message", Constants.OTHER_EXCEPTION);
            }
            return new ModelAndView("error");
        }

    }
}
