package com.nirvana.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--28--10:26
 **/
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
