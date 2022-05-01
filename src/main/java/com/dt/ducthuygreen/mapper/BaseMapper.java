package com.dt.ducthuygreen.mapper;

import java.util.List;

public interface BaseMapper<Model, DTO> {
    
    DTO toDTO(Model model);

    Model toPersistenceBean(DTO dto);

    List<Model> toListModel(List<DTO> dtoList);

    List<DTO> toListDTO(List<Model> list);

}
