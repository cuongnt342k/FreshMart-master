package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.OrdersStatistics;
import com.dt.ducthuygreen.services.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics-orders")
public class StatisticsController {
    @Autowired
    IStatisticsService statisticsService;

    @GetMapping
    public Page<OrdersStatistics> getOrder(Pageable pageable){
        return statisticsService.findOrdersStatistics(pageable);
    }
    @GetMapping("/category")
    public Page<OrdersStatistics> getCategory(Pageable pageable){
        return statisticsService.findCategoryStatistics(pageable);
    }
    @GetMapping("/sales")
    public List<OrdersStatistics> getSales(){
        return statisticsService.findSaleStatistics();
    }

}
