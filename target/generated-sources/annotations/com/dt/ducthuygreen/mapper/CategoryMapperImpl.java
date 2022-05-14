package com.dt.ducthuygreen.mapper;

import com.dt.ducthuygreen.dto.CategoryDTO;
import com.dt.ducthuygreen.entities.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-06T23:06:38+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 12 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDTO(Category model) {
        if ( model == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryName( model.getCategoryName() );
        categoryDTO.setDescription( model.getDescription() );

        return categoryDTO;
    }

    @Override
    public Category toPersistenceBean(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( dto.getCategoryName() );
        category.setDescription( dto.getDescription() );

        return category;
    }

    @Override
    public List<Category> toListModel(List<CategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( dtoList.size() );
        for ( CategoryDTO categoryDTO : dtoList ) {
            list.add( toPersistenceBean( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> toListDTO(List<Category> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryDTO> list1 = new ArrayList<CategoryDTO>( list.size() );
        for ( Category category : list ) {
            list1.add( toDTO( category ) );
        }

        return list1;
    }
}
