package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.Role;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.mapper.UserMapper;
import com.dt.ducthuygreen.repos.RoleRepository;
import com.dt.ducthuygreen.repos.UserRepository;
import com.dt.ducthuygreen.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findUserByDeletedFalse(pageable);
    }

    @Override
    public Page<User> getAllByText(Pageable pageable, String text) {
        return userRepository.findUserByDeletedIsFalseAndUsernameContainsOrFullNameContainsAndDeletedIsFalseOrEmailContainsAndDeletedIsFalse(pageable, text, text, text);
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
    public String update(UserUpdateDTO userDTO) {
        User user = userRepository.findUserByIdAndDeletedFalse(userDTO.getId());
        if (userRepository.findUserByEmailAndDeletedFalse(userDTO.getEmail()) != null && !user.getEmail().equals(userDTO.getEmail())) {
            return "Email đã tồn tại.";
        }
        if (userRepository.findByUsername(userDTO.getUsername()) != null && !user.getUsername().equals(userDTO.getUsername())) {
            return "Username đã tồn tại.";
        }
        if (user != null) {
            userMapper.editDtoToModel(userDTO, user);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.getRoles().clear();
            for (Role role : userDTO.getRoles()) {
                role = roleRepository.findRoleById(role.getId());
                user.getRoles().add(role);
            }
            userRepository.save(user);
            return "Successfully";
        }
        return "Error";
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public String deleteById(Long userId) {
        User user = userRepository.findUserByIdAndDeletedFalse(userId);
        if (user != null){
            user.setDeleted(true);
            userRepository.save(user);
            return "Successfully";
        }
        return "Error";
    }

    @Override
    public void createAdminAccount(User user) {
        // TODO Auto-generated method stub
    }

}
