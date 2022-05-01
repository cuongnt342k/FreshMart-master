package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.dto.OrdersStatistics;
import com.dt.ducthuygreen.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StatisticsRepository extends BaseRepository<Item, Long> {

    @Query("SELECT new com.dt.ducthuygreen.dto.OrdersStatistics(i.product,SUM (i.quantity)) "
            + "FROM Item AS i WHERE i.deleted = false AND i.status = true AND i.createdDate between :start and :end GROUP BY i.product ORDER BY SUM (i.quantity) DESC")
    Page<OrdersStatistics> findOrdersStatistics(Pageable pageable, Date start, Date end);

    @Query("SELECT new com.dt.ducthuygreen.dto.OrdersStatistics(c,SUM (i.quantity)) "
            + "FROM Item AS i INNER JOIN i.product p INNER JOIN p.category c WHERE i.deleted = false AND i.status = true AND i.createdDate between :start and :end GROUP BY c ORDER BY SUM (i.quantity) DESC")
    Page<OrdersStatistics> findCategoryStatistics(Pageable pageable, Date start, Date end);

    @Query("SELECT new com.dt.ducthuygreen.dto.OrdersStatistics(SUM (o.totalPrice)) "
            + "FROM Order AS o  WHERE o.deleted = false GROUP BY FUNCTION('YEAR', o.createdDate),FUNCTION('MONTH', o.createdDate)  ORDER BY FUNCTION('YEAR', o.createdDate),FUNCTION('MONTH', o.createdDate)" )
    List<OrdersStatistics> findSaleStatistics();
}
