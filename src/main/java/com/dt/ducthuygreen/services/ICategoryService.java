package com.dt.ducthuygreen.services;

import java.util.List;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ICategoryService {
		List<Category> findAll();

		Page<Category> findAllBySearch(Pageable pageable, String textSearch);

		Page<Category> findAllPageable(Pageable pageable);

	 	Category findById(Long categoryId);

	    Category findBySlug(String slug);

	    Category create(CategoryDTO categoryDTO, MultipartFile file );

	    Category update(CategoryUpdateDTO categoryUpdateDTO, Category currenCategory, MultipartFile file);

	    String deleteById(Long categoryId);

}
