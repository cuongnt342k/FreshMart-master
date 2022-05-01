package com.dt.ducthuygreen.mapper;

import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserUpdateDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void editDtoToModel(UserUpdateDTO userDTO, @MappingTarget User user);
}
