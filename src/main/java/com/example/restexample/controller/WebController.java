package com.example.restexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/login")
    public String loginPage(Model model){
        return "login";
    }

    @RequestMapping("/startpage")
    public String startPage(Model model){
        return "startpage";
    }
}
