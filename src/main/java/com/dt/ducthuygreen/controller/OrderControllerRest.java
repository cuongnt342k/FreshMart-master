package com.dt.ducthuygreen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.services.IOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order-items")
public class OrderControllerRest {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllCartByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(200).body(orderService.getAllOrderByUserId(userId));
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

    @DeleteMapping("/deleteOrder/{orderId}")
    public String deleteOrderById(@PathVariable("orderId") Long orderId) {
        if (orderService.deleteOrderById(orderId)) return "Successfully";
        return "Error";
    }
}
