package com.dt.ducthuygreen.services;

import com.dt.ducthuygreen.dto.OrdersStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStatisticsService {
    Page<OrdersStatistics> findOrdersStatistics(Pageable pageable);

    Page<OrdersStatistics> findCategoryStatistics(Pageable pageable);

    List<OrdersStatistics> findSaleStatistics();
}
