package com.dt.ducthuygreen.services.impl;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginException;

import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.repos.CartRepository;
import com.dt.ducthuygreen.repos.RoleRepository;
import com.dt.ducthuygreen.repos.UserRepository;
import com.dt.ducthuygreen.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.Utils.JwtUtil;
import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.entities.Role;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.exception.DuplicateException;
import com.dt.ducthuygreen.payload.AuthenticationRequest;
import com.dt.ducthuygreen.services.AuthService;
import com.dt.ducthuygreen.services.IRoleService;
import com.dt.ducthuygreen.services.IUserService;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService IUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Boolean login(AuthenticationRequest request) throws LoginException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new LoginException("Incorrect username or password");
        }

//        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
//        //    final String jwt = jwtUtil.generateToken(userDetails);
//
//        User user = userService.findByUsername(request.getUsername());
//       authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        //      List<String> roles = new ArrayList<>();
//        Set<Role> roleSet = user.getRoles();
//        if(roleSet.size() > 0) {
//            roleSet.forEach(item -> roles.add(item.getRoleName()));
//        }

        // return new AuthenticationResponse(jwt, user.getId(), user.getUsername(), roles);
        return true;
    }

//    @Override
//    public Boolean validateToken(AuthenticationResponse authenticationResponse) throws InvalidObjectException {
//        try {
//            String jwt = authenticationResponse.getJwt();
//            String username = jwtUtil.extractUsername(jwt);
//            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//
//            return jwtUtil.validateToken(jwt, userDetails);
//        } catch (Exception e) {
//            throw new InvalidObjectException(e.getMessage());
//        }
//    }

    @Override
    public Boolean register(UserDTO userDTO) throws InvalidObjectException {
        User oldUser = IUserService.findByUsername(userDTO.getUsername());
        if (oldUser != null) {
            throw new DuplicateException("Username has already exists");
        }
        User user = new User();
        user = ConvertObject.convertUserDTOToUser(user, userDTO);
        if (user == null) {
            throw new InvalidObjectException("Invalid user");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRoles() == null) {
            Role role = roleService.getRoleByName("ROLE_MEMBER");
            user.setRoles(Set.of(role));
        } else {
            List<Role> roleList = new ArrayList<>();
            userDTO.getRoles().forEach(role ->{
                Role r = roleRepository.findRoleById(role.getId());
                roleList.add(r);
            });
            user.getRoles().addAll(roleList);
        }
        user.setStatus(1);
        Cart cart = new Cart();
        cart.setUserName(user.getUsername());
        cartRepository.save(cart);
        user.setCart(cart);
        user.setDeleted(false);
        IUserService.save(user);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        return true;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmailAndDeletedFalse(email);
    }

}
