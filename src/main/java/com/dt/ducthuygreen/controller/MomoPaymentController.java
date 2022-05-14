package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MomoPaymentController {

    @RequestMapping("/momo-payment")
    public String paymentWithMomo(@ModelAttribute OrderDTO orderDTO){

        String endpoit = "";
        String partnerCode;
        String accessKey;
        String returnUrl;
        String notifyUrl;

        String amount  = orderDTO.getTotalPrice().toString();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        String extraData = "";

        return null;
    }

}
