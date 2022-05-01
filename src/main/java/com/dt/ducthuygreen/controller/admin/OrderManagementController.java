package com.dt.ducthuygreen.controller.admin;

import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.entities.Order;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.services.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OrderManagementController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public String listOrderPage() {
        return "/management/list-order";
    }

    @GetMapping("/editOrder/{id}")
    public String editOrderPage(Model model, @PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "/management/edit-order-form";
    }

}
