package com.dt.ducthuygreen.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.SignupDTO;
import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.repos.UserRepository;
import com.dt.ducthuygreen.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Long countAll() {
		return userRepository.count();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User create(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAdminAccount(User user) {
		// TODO Auto-generated method stub
		
	}
	
}
