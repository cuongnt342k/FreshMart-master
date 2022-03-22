package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.BaseModel;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateDTO {
	private String productName;

    private String image;

    private Long price;

    private String description;

    private Integer evaluate;

    private Integer quantity;

    private Integer sold;

}
