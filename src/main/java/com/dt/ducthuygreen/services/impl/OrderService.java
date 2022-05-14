package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Item;
import com.dt.ducthuygreen.entities.Order;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.mapper.OrderMapper;
import com.dt.ducthuygreen.repos.ItemRepository;
import com.dt.ducthuygreen.repos.OrderRepository;
import com.dt.ducthuygreen.services.ICartService;
import com.dt.ducthuygreen.services.IOrderService;
import com.dt.ducthuygreen.services.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IUserService IUserService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private OrderMapper orderMapper;


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
        Order order = new Order();
        ConvertObject.convertOrderDTOToOrder(order, orderDTO);
        order.setUserName(userName);

        Cart cart = cartService.getCartByUserName(userName);
        List<Item> items = cart.getItems();
        if (items.size() == 0) {
            throw new NotFoundException("Not containt cart, can not create order");
        }
        for (Item item : items) {
            if (item.getOrder() == null) {
                item.setOrder(order);
                item.setStatus(true);
            }
        }
        order.setDeleted(false);
        order.setCreatedBy(userName);
        order.setStatus(false);
        order.setConfirm(0);
        return orderRepository.save(order);
    }

    @Override
    public Order createNewOrder2(String userName, OrderDTO orderDTO) {
        Order order = new Order();
        ConvertObject.convertOrderDTOToOrder(order, orderDTO);
        order.setUserName(userName);

        Cart cart = cartService.getCartByUserName(userName);
        List<Item> items = cart.getItems();
        if (items.size() == 0) {
            throw new NotFoundException("Not containt cart, can not create order");
        }
        long totalPrice = 0L;
        long totalQuantity = 0L;
        for (Item item : items) {
            if (item.getOrder() == null && !item.getStatus()) {
                item.setOrder(order);
                item.setStatus(true);
                totalPrice += item.getPrice();
                totalQuantity += item.getQuantity();
            }
        }
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setDeleted(false);
        order.setCreatedBy(userName);
        order.setStatus(false);
        order.setConfirm(0);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).get();
        ConvertObject.convertOrderDTOToOrder(order, orderDTO);
        return orderRepository.save(order);
    }

//    @Override
//    public List<Order> getAllOrderByUserId(Long userId) {
//        return orderRepository.findAll().stream().filter(item -> item.getUserId() == userId).collect(Collectors.toList());
//    }

    @Override
    public boolean deleteOrderById(Long orderId) {
        Order order = getOrderById(orderId);

        try {
            if (order.getConfirm() == 2) {
                order.setDeleted(true);
                orderRepository.save(order);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.debug(e);
            return false;
        }
    }

    @Override
    public Order changeStatus(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(!order.get().getStatus());
            orderRepository.save(order.get());
        }
        return order.get();
    }

    @Override
    public Order detailOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            List<Item> items = itemRepository.findItemByOrder(order.get());
            order.get().setItems(items);
        }
        return order.get();
    }

    @Override
    public Order cancelOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.get().setConfirm(2);
        orderRepository.save(order.get());
        return order.get();
    }

    @Override
    public Order confirmOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.get().setConfirm(3);
        orderRepository.save(order.get());
        return order.get();
    }

}
