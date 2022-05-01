package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.mapper.OrderMapper;
import com.dt.ducthuygreen.repos.OrderRepository;
import com.dt.ducthuygreen.services.IOrderService;
import com.dt.ducthuygreen.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderControllerRest {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IUserService userService;

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getAllCartByUserId(@PathVariable("userId") Long userId) {
//        return ResponseEntity.status(200).body(orderService.getAllOrderByUserId(userId));
//    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<?> getDetailOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.status(200).body(orderService.detailOrder(orderId));
    }

    @GetMapping("/history/{userName}")
    public Page<OrderDTO> getAllOrderByUserName(Pageable pageable, @PathVariable("userName") String userName) {
        return orderRepository.findAllByUserNameAndDeletedFalse(pageable, userName).map(orderMapper::toDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllCart(Pageable pageable, @RequestParam(required = false) String textSearch) {
        if (textSearch != null)
            return ResponseEntity.status(200).body(orderService.getAllOrderByTextSearch(pageable, textSearch));
        return ResponseEntity.status(200).body(orderService.getAllOrder(pageable));
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("userName") String userName) {
        return ResponseEntity.status(201).body(orderService.createNewOrder(userName, orderDTO));
    }

    @PutMapping("/editOrder")
    public ResponseEntity<?> editOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(201).body(orderService.updateOrder(orderDTO));
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatusOrder(@PathVariable("id") Long id) {
        return ResponseEntity.status(201).body(orderService.changeStatus(id));
    }

    @PutMapping("/cancelOrder/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id) {
        return ResponseEntity.status(201).body(orderService.cancelOrder(id));
    }

    @PutMapping("/confirmOrder/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") Long id) {
        return ResponseEntity.status(201).body(orderService.confirmOrder(id));
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public String deleteOrderById(@PathVariable("orderId") Long orderId) {
        if (orderService.deleteOrderById(orderId)) return "Successfully";
        return "Error";
    }
}
