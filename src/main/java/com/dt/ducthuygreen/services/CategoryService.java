package com.dt.ducthuygreen.services;

import java.util.List;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;

public interface CategoryService {
		List<Category> findAll();
	
	 	Category findById(Long categoryId);

	    Category findBySlug(String slug);

	    Category create(CategoryDTO categoryDTO);

	    Category update(CategoryUpdateDTO categoryUpdateDTO, Category currenCategory);

	    
	    void deleteById(Long categoryId);

}
