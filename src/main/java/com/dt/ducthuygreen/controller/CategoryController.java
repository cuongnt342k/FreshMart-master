package com.dt.ducthuygreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cateID", id);
        return "product";
    }

}
