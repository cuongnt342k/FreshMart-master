package com.dt.ducthuygreen.services;

import java.util.List;

import com.dt.ducthuygreen.dto.CartDTO;
import com.dt.ducthuygreen.entities.Cart;

public interface ICartService {
	Cart getById(Long id);
	Cart createNewCart(CartDTO cartDTO, Long userId);
	public Cart getCartByUserName(String userId);
	void deleteItemByUserId(Long userId);
	void deleteCartById(Long id);
//	void deleteCartByUserId(Long userId);
	Cart save(Cart cart);
}
