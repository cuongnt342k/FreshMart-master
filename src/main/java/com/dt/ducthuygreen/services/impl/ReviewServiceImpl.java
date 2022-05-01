package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.dto.ReviewDTO;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.entities.Review;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.mapper.ReviewMapper;
import com.dt.ducthuygreen.repos.ProductRepository;
import com.dt.ducthuygreen.repos.ReviewRepository;
import com.dt.ducthuygreen.repos.UserRepository;
import com.dt.ducthuygreen.services.IReviewService;
import com.dt.ducthuygreen.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Review addReview(ReviewDTO reviewDTO) {
        Optional<Product> product = productRepository.findById(reviewDTO.getProduct().getId());
        User user = userRepository.findByUsername(reviewDTO.getUser().getUsername());
        Review review = new Review();
        if (product.isPresent() && user != null){
            review = reviewMapper.toPersistenceBean(reviewDTO);
            review.setProduct(product.get());
            review.setUser(user);
        }
        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> reviewList(Pageable pageable, Long productID) {
        Optional<Product> product = productRepository.findById(productID);
        return product.map(value -> reviewRepository.findAllByDeletedFalseAndProduct(pageable, value)).orElse(null);
    }


}
