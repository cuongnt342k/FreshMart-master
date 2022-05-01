package com.dt.ducthuygreen.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dt.ducthuygreen.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	Category findByCategoryName(String categoryName);

	Page<Category> findCategoryByDeletedIsFalse(Pageable pageable);

	Page<Category> findCategoryByDeletedIsFalseAndCategoryNameContains(Pageable pageable, String categoryName);
}
