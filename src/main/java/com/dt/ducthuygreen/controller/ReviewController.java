package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.ReviewDTO;
import com.dt.ducthuygreen.entities.Review;
import com.dt.ducthuygreen.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    IReviewService reviewService;

    @PostMapping("/addReview")
    @ResponseBody
    public ResponseEntity<?> createReview(
            @Valid @RequestBody ReviewDTO reviewDTO
    ) {
        Review review = reviewService.addReview(reviewDTO);
        return ResponseEntity.status(201).body(review);
    }

    @GetMapping("/{productID}")
    @ResponseBody
    public Page<Review> findAll(Pageable pageable, @PathVariable("productID") Long productID){
        return reviewService.reviewList(pageable, productID);
    }
}
