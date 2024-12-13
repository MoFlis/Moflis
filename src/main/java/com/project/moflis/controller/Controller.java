package com.project.moflis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    @RequestMapping("hi")
    public String hello() {
        return "hi";
    }
    
}
