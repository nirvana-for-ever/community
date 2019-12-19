package com.nirvana.community.controller;

import com.nirvana.community.common.Constants;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 处理error
 * @author: Nirvana
 * @create: 2019--12--19--13:49
 **/
@Controller
@RequestMapping("/error")
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handle(HttpServletRequest request, Model model){
        HttpStatus httpStatus = getStatus(request);
        //4开头的错误
        if (httpStatus.is4xxClientError()){
            model.addAttribute("message", Constants.FOURXX_ERROR);
        //5开头的错误
        }else if (httpStatus.is5xxServerError()){
            model.addAttribute("message",Constants.FIVEXX_ERROR);
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {

        //返回状态码
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (null == statusCode) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        //根据状态码查找错误的信息
        return HttpStatus.valueOf(statusCode);
    }

}
