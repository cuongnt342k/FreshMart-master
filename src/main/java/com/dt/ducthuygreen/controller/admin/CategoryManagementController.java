package com.dt.ducthuygreen.controller.admin;

import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CategoryManagementController {

    @Autowired
    ICategoryService ICategoryService;

    @GetMapping
    public String listCategoryPage() {
        return "/management/list-category";
    }

    @GetMapping("/addCategory")
    public String addCategoryPage() {
        return "/management/add-category-form";
    }

    @GetMapping("/editCategory/{id}")
    public String editCategoryPage(Model model, @PathVariable("id") Long categoryId) {
        Category category = ICategoryService.findById(categoryId);
        model.addAttribute("category", category);
        return "/management/edit-category-form";
    }

}
