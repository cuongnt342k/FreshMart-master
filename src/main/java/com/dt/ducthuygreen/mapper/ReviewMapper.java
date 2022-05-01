package com.dt.ducthuygreen.mapper;

import com.dt.ducthuygreen.dto.ReviewDTO;
import com.dt.ducthuygreen.entities.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<Review, ReviewDTO>{
}
