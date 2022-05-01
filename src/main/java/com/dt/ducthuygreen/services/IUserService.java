package com.dt.ducthuygreen.services;

import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
	Long countAll();
	
	List<User> getAllUsers();

	Page<User> getAll(Pageable pageable);

    Page<User> getAllByText(Pageable pageable, String text);

    User findById(Long userId);

    User findByUsername(String username);

    User create(UserDTO userDTO);

    String update(UserUpdateDTO userDTO);

    User save(User user);

    String deleteById(Long userId);

    void createAdminAccount(User user);

}
