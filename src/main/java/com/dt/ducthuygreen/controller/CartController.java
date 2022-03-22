package com.dt.ducthuygreen.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @GetMapping("/")
    public String cartPage(){

        return "cart-management";
    }
    @PostMapping("/addToCart")
    public String addToCart(){

        return null;
    }

}
