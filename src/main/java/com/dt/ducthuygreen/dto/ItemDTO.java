package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {
//	private String size;
	private Long id;
	private Integer quantity;
	private Boolean status;
	private Long price;
	Product product;
	Cart cart;
	
}
