package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dt.ducthuygreen.services.IUserService;

@RestController
@RequestMapping("/api/users")
//@SecurityRequirement(name = "Authorization")
public class UserController {
	@Autowired
	private IUserService IUserService;
	
	@GetMapping
	public ResponseEntity<?> getAllUser(Pageable pageable, @RequestParam(required = false) String textSearch){
		if (textSearch != null){
			return ResponseEntity.status(200).body(IUserService.getAllByText(pageable, textSearch));
		}
		return ResponseEntity.status(200).body(IUserService.getAll(pageable));
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
		return ResponseEntity.status(200).body(IUserService.getAllUsers());
	}



}
