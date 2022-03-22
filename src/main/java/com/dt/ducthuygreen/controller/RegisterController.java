package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.exception.DuplicateException;
import com.dt.ducthuygreen.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InvalidObjectException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private AuthService authService;

    @GetMapping
    public String registerPage() {
        return "register";
    }

    @PostMapping
    @ResponseBody
    public String register(@Valid @RequestBody UserDTO userDTO) throws InvalidObjectException {
        try {
            authService.register(userDTO);
        }catch (DuplicateException e){
            return e.getMessage();
        }
        return "Successfully";
    }
//    @PostMapping
//    public String login(@RequestBody AuthenticationRequest authenticationRequest) throws LoginException {
//        authService.login(authenticationRequest);
//        return "index";
//    }
}
