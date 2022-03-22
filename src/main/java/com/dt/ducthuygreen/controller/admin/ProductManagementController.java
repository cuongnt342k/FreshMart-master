package com.dt.ducthuygreen.controller.admin;

import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.services.CategoryService;
import com.dt.ducthuygreen.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductManagementController {

    @Autowired
    ProductServices productServices;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String listProductPage() {
        return "/management/list-product";
    }

    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "/management/add-product-form";
    }

    @GetMapping("/editProduct/{id}")
    public String editProductPage(Model model, @PathVariable("id") Long productId) {
        List<Category> categoryList = categoryService.findAll();
        Product product = productServices.getProductById(productId);
        model.addAttribute("categories", categoryList);
        model.addAttribute("product", product);
        return "/management/edit-product-form";
    }

}
