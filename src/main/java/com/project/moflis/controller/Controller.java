package com.project.moflis.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    @GetMapping("hi")
    public String getMethodName() {
        return "하이";
    }
    
    
}
