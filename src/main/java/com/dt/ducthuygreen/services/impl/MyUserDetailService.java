package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.dto.MyUserDetail;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user by username: " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(item -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(item.getRoleName()));
        });

        return new MyUserDetail(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}