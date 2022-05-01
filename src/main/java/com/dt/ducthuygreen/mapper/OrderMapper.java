package com.dt.ducthuygreen.mapper;

import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderDTO>{
}
