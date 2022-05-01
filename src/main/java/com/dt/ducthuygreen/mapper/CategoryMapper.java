package com.dt.ducthuygreen.mapper;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDTO> {

}
