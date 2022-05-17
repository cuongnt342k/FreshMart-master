package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getAllByDeletedIsFalse(Pageable pageable);

    Page<Product> getAllByDeletedIsFalseAndCategoryId(Pageable pageable, Long id);

    Page<Product> findProductsByProductNameContainsAndDeletedIsFalse(Pageable pageable, String textSearch);

    Page<Product> findProductsByCategoryInAndDeletedFalse(Pageable pageable, List<Category> categoryList);

    Page<Product> findProductsByPriceBetweenAndDeletedFalse(Pageable pageable, Long first, Long second);
}
