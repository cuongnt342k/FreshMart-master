package com.dt.ducthuygreen.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.exception.DuplicateException;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.CategoryRepository;
import com.dt.ducthuygreen.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.size() == 0) {
            throw new NotFoundException("Not found any category");
        }
        return categories;
    }

    @Override
    public Category findById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException("Not found this category id");
        }
        return category.get();
    }

    @Override
    public Category findBySlug(String slug) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category create(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (category != null) {
            throw new DuplicateException("Category " + categoryDTO.getCategoryName() + " had already exists");
        }
        category = new Category(categoryDTO.getCategoryName(), categoryDTO.getDescription(), null, null);
        Category category2 = categoryRepository.save(category);
        return category2;
    }

    @Override
    public Category update(CategoryUpdateDTO categoryUpdateDTO, Category currentCategory) {
        if (categoryUpdateDTO.getDeleted() != null) {
            currentCategory.setDeleted(categoryUpdateDTO.getDeleted());
        }

        if (categoryUpdateDTO.getCreatedBy() != null) {
            currentCategory.setCreatedBy(categoryUpdateDTO.getCreatedBy());
        }

        if (categoryUpdateDTO.getCreatedDate() != null) {
            currentCategory.setCreatedDate(categoryUpdateDTO.getCreatedDate());
        }

        if (categoryUpdateDTO.getUpdatedBy() != null) {
            currentCategory.setUpdatedBy(categoryUpdateDTO.getUpdatedBy());
        }

        if (categoryUpdateDTO.getUpdatedDate() != null) {
            currentCategory.setUpdatedDate(categoryUpdateDTO.getUpdatedDate());
        }

        if (categoryUpdateDTO.getDescription() != null) {
            currentCategory.setDescription(categoryUpdateDTO.getDescription());
        }

        if (categoryUpdateDTO.getCategoryName() != null) {
            currentCategory.setCategoryName(categoryUpdateDTO.getCategoryName());
        }

        Category category = categoryRepository.save(currentCategory);

        System.out.println(categoryUpdateDTO.getDescription());
        return category;
    }

    @Override
    public void deleteById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException("Not Found Any Category with this id");
        }
        categoryRepository.deleteById(categoryId);
    }

}
