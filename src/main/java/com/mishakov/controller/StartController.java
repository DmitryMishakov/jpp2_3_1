package com.mishakov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/start")
public class StartController {

    @GetMapping("/get")
    @ResponseBody
    public String getStart() {
        return "index";
    }
}
