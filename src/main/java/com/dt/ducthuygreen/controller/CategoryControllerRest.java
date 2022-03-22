package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerRest {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<?> getAllCategories(){
		return ResponseEntity.status(200).body(categoryService.findAll());
	}
	@PostMapping
	public ResponseEntity<?> createAnCategory(@RequestBody CategoryDTO categoryDTO){
		return ResponseEntity.status(201).body(categoryService.create(categoryDTO));		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO, @PathVariable("id") Long id){
		Category currenCategory= categoryService.findById(id);
		Category category2= categoryService.update(categoryUpdateDTO, currenCategory);
		return ResponseEntity.status(200).body(category2);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
		categoryService.deleteById(id);
		return ResponseEntity.status(200).build();
	}

}
