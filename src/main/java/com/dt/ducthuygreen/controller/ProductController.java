package com.dt.ducthuygreen.controller;


import com.dt.ducthuygreen.services.IProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductServices IProductServices;

    @GetMapping("/")
    public String productPage() {
        return "product";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", IProductServices.getProductById(id));
        return "detail";
    }
}
