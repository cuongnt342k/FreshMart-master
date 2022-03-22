package com.dt.ducthuygreen.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.SignupDTO;
import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.User;

public interface UserService {
	Long countAll();
	
	List<User> getAllUsers();

    User findById(Long userId);

    User findByUsername(String username);

    User create(UserDTO userDTO);
    
    User save(User user);

    void deleteById(Long userId);

    void createAdminAccount(User user);

}
