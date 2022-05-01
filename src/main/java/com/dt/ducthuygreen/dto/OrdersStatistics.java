package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrdersStatistics {
    private Product product;
    private Category category;
    private Long totalQuantity;

    public OrdersStatistics(Product product, Long totalQuantity) {
        this.product = product;
        this.totalQuantity = totalQuantity;
    }

    public OrdersStatistics(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public OrdersStatistics(Category category, Long totalQuantity) {
        this.category = category;
        this.totalQuantity = totalQuantity;
    }
}
