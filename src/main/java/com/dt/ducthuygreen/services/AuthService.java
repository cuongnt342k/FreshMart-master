package com.dt.ducthuygreen.services;

import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.payload.AuthenticationRequest;

import javax.security.auth.login.LoginException;
import java.io.InvalidObjectException;

public interface AuthService {
	Boolean login(AuthenticationRequest request) throws LoginException;
	Boolean register(UserDTO userDTO) throws InvalidObjectException;
	User findByEmail(String email);
//    Boolean validateToken(AuthenticationResponse authenticationResponse) throws InvalidObjectException;
}
