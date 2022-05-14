package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.entities.Order;
import com.dt.ducthuygreen.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/check-out")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @GetMapping("/")
    public String checkoutPage() {

        return "check-out";
    }

    @GetMapping("/test")
    public String checkoutPage2(Model model) {
        OrderDTO order = new OrderDTO();
        model.addAttribute("order", order);
        return "check-out-2";
    }

    @GetMapping("/history")
    public String orderHistory() {

        return "order-history";
    }

    @PostMapping("/save")
    public String createNewOrder2(@ModelAttribute OrderDTO order, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {

        }
        Order o = orderService.createNewOrder2(principal.getName(), order);
        String message = "Đặt hàng thành công. Mã đơn hàng là: " + o.getId().toString();
        model.addAttribute("message", message);
        return "redirect:/check-out/history";
    }
}
