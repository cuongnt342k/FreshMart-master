package com.dt.ducthuygreen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dt.ducthuygreen.dto.CartDTO;
import com.dt.ducthuygreen.services.ICartService;
import com.dt.ducthuygreen.services.impl.CartServiceImpl;

@RestController
@RequestMapping("/api/carts")
public class CartControllerRest {
	@Autowired
	private ICartService cartService;

	@GetMapping("/{userName}")
	public ResponseEntity<?> getAllCart(@PathVariable("userName") String userName) {
		return ResponseEntity.status(200).body(cartService.getCartByUserName(userName));
	}

	@PostMapping("/{userId}")
	public ResponseEntity<?> createNewCart(@PathVariable("userId") Long userId, @RequestBody CartDTO cartDTO) {
		return ResponseEntity.status(201).body(cartService.createNewCart(cartDTO, userId));
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<?> deleteCartById(@PathVariable("cartId") Long cartId) {
		cartService.deleteCartById(cartId);
		return ResponseEntity.status(200).build();
	}

	@DeleteMapping("/{userId}/user")
	public ResponseEntity<?> deleteCartByUserId(@PathVariable("userId") Long userId) {
		cartService.deleteItemByUserId(userId);
		return ResponseEntity.status(200).build();
	}

}
