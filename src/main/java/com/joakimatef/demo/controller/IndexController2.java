package com.joakimatef.demo.controller;

import com.joakimatef.demo.security.permission.UserCreatePermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController2 {


    @UserCreatePermission
    @GetMapping({"/index2.html"})
    public String index(){
        return "index2";
    }
}
