package com.dt.ducthuygreen.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dt.ducthuygreen.entities.Item;
import com.dt.ducthuygreen.repos.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Order;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.OrderRepository;
import com.dt.ducthuygreen.services.ICartService;
import com.dt.ducthuygreen.services.IOrderService;
import com.dt.ducthuygreen.services.UserService;

@Service
@Log4j2
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> optional = orderRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Order> getAllOrder(Pageable pageable) {
        return orderRepository.findAllByDeletedIsFalse(pageable);
    }

    @Override
    public Page<Order> getAllOrderByTextSearch(Pageable pageable, String textSearch) {
        return orderRepository.findAllByDeletedIsFalseAndAddressContainsOrDeletedIsFalseAndEmailContainsOrDeletedIsFalseAndPostcodeContainsOrDeletedIsFalseAndFirstNameContainsOrDeletedIsFalseAndLastNameContains(pageable, textSearch, textSearch, textSearch, textSearch, textSearch);
    }

    @Override
    public Order createNewOrder(String userName, OrderDTO orderDTO) {
        User user = userService.findByUsername(userName);
        if (user == null) {
            throw new NotFoundException("Can not find userid");
        }
        Order order = new Order();
        ConvertObject.convertOrderDTOToOrder(order, orderDTO);
        order.setUser_id(user.getId());

        Cart cart = user.getCart();
        List<Item> items = cart.getItems();
        if (items.size() == 0) {
            throw new NotFoundException("Not containt cart, can not create order");
        }
        for (Item item : items) {
            item.setOrder(order);
            item.setStatus(false);
        }
        order.setDeleted(false);
        order.setCreatedBy(user.getUsername());
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).get();
        ConvertObject.convertOrderDTOToOrder(order, orderDTO);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrderByUserId(Long userId) {
        return orderRepository.findAll().stream().filter(item -> item.getUser_id() == userId).collect(Collectors.toList());
    }

    @Override
    public boolean deleteOrderById(Long orderId) {
        Order order = getOrderById(orderId);
        try {
            order.setDeleted(true);
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            log.debug(e);
            return false;
        }
    }

}
