package com.mishakov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/start")
public class StartController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getStart() {
        return "index";
    }
}
