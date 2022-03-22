package com.dt.ducthuygreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/check-out")
public class OrderController {
    @GetMapping("/")
    public String cartPage(){

        return "check-out";
    }
}
