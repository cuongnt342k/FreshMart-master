package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getAllByDeletedIsFalse(Pageable pageable);

    Page<Product> getAllByDeletedIsFalseAndCategoryId(Pageable pageable, Long id);

    Page<Product> findProductsByProductNameContainsAndDeletedIsFalse(Pageable pageable, String textSearch);

}
