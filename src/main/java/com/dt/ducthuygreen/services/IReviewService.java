package com.dt.ducthuygreen.services;

import com.dt.ducthuygreen.dto.ReviewDTO;
import com.dt.ducthuygreen.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReviewService {

    Review addReview(ReviewDTO reviewDTO);

    Page<Review> reviewList(Pageable pageable, Long productID);

}
