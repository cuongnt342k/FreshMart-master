package com.dt.ducthuygreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String HomePage() {
        return "index";
    }

    @GetMapping("403")
    public String accessDeniedPage() {
        return "/403";
    }

    @GetMapping("contact")
    public String contactPage() {
        return "/contact";
    }

    @GetMapping("about-us")
    public String aboutPage() {
        return "/about-us";
    }
}
