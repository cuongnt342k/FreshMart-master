package com.dt.ducthuygreen.services.impl;

import java.util.List;
import java.util.Optional;

import com.dt.ducthuygreen.Utils.UploadFile;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.dto.CategoryUpdateDTO;
import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.exception.DuplicateException;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.CategoryRepository;
import com.dt.ducthuygreen.services.ICategoryService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UploadFile uploadFile;

    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.size() == 0) {
            throw new NotFoundException("Not found any category");
        }
        return categories;
    }

    @Override
    public Page<Category> findAllBySearch(Pageable pageable, String textSearch) {
        return categoryRepository.findCategoryByDeletedIsFalseAndCategoryNameContains(pageable,textSearch);
    }

    @Override
    public Page<Category> findAllPageable(Pageable pageable) {
        return categoryRepository.findAll(pageable);
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
    public Category create(CategoryDTO categoryDTO,  MultipartFile file) {
        Category category = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (category != null) {
            throw new DuplicateException("Category " + categoryDTO.getCategoryName() + " had already exists");
        }
        Category cate = new Category(categoryDTO.getCategoryName(), categoryDTO.getDescription(), null, null);
        if (file != null) {
            cate.setImage(uploadFile.getUrlFromFile(file));
        }
        Category category2 = categoryRepository.save(cate);
        return category2;
    }

    @Override
    public Category update(CategoryUpdateDTO categoryUpdateDTO, Category currentCategory, MultipartFile file) {

        if (categoryUpdateDTO.getDescription() != null) {
            currentCategory.setDescription(categoryUpdateDTO.getDescription());
        }

        if (categoryUpdateDTO.getCategoryName() != null) {
            currentCategory.setCategoryName(categoryUpdateDTO.getCategoryName());
        }
        if (file != null) {
            currentCategory.setImage(uploadFile.getUrlFromFile(file));
        }
        Category category = categoryRepository.save(currentCategory);

        System.out.println(categoryUpdateDTO.getDescription());
        return category;
    }

    @Override
    public String deleteById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        Page<Product> products = productRepository.getAllByDeletedIsFalseAndCategoryId(null,categoryId);
        if (products.getSize() != 0){
            return "Can't remove this records because has products";
        }
        if (!category.isPresent()) {
            return "Not found";
        }
        categoryRepository.deleteById(categoryId);
        return "Successfully";
    }

}
