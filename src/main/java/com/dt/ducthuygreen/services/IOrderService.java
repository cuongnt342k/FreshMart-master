package com.dt.ducthuygreen.services;

import java.util.List;

import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Order getOrderById(Long id);

    Page<Order> getAllOrder(Pageable pageable);

    Page<Order> getAllOrderByTextSearch(Pageable pageable, String textSearch);

    Order createNewOrder(String userName, OrderDTO orderDTO);

    Order createNewOrder2(String userName, OrderDTO orderDTO);

    Order updateOrder(OrderDTO orderDTO);

//    List<Order> getAllOrderByUserId(Long userId);

    boolean deleteOrderById(Long orderId);

    Order cancelOrder(Long id);

    Order confirmOrder(Long id);

    Order changeStatus(Long id);

    Order detailOrder(Long id);
}
