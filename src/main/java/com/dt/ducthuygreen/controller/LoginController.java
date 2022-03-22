package com.dt.ducthuygreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
//    @Autowired
//    private AuthService authService;

    @GetMapping
    public String loginPage() {
        return "login";
    }
//    @PostMapping
//    public String login(@RequestBody AuthenticationRequest authenticationRequest) throws LoginException {
//        authService.login(authenticationRequest);
//        return "index";
//    }
}



