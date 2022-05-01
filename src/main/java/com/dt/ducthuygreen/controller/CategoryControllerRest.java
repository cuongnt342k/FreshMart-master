package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerRest {
    @Autowired
    private ICategoryService ICategoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(Pageable pageable, @RequestParam(required = false) String textSearch) {
        if (textSearch != null)
            return ResponseEntity.status(200).body(ICategoryService.findAllBySearch(pageable, textSearch));
        return ResponseEntity.status(200).body(ICategoryService.findAllPageable(pageable));
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> createAnCategory(@Valid @ModelAttribute CategoryDTO categoryDTO, @RequestParam(name = "multipartFile", required = false) MultipartFile image) {
        return ResponseEntity.status(201).body(ICategoryService.create(categoryDTO, image));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@Valid @ModelAttribute CategoryUpdateDTO categoryUpdateDTO, @RequestParam(name = "multipartFile", required = false) MultipartFile image) {
        Category currenCategory = ICategoryService.findById(categoryUpdateDTO.getId());
        Category category2 = ICategoryService.update(categoryUpdateDTO, currenCategory, image);
        return ResponseEntity.status(200).body(category2);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        return ICategoryService.deleteById(id);
    }

}
