package com.dt.ducthuygreen.dto;

import java.util.ArrayList;
import java.util.List;

import com.dt.ducthuygreen.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
	private Long quantity;
	private List<Item> items = new ArrayList<>();
}
