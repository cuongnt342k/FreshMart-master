package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository extends BaseRepository<Review, Long> {

    Page<Review> findAllByDeletedFalseAndProduct(Pageable pageable, Product product);

}
