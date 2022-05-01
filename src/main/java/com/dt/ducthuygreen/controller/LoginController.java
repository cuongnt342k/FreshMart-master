package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.Utils.RestFB;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping
public class LoginController {
    //    @Autowired
//    private AuthService authService;

    @Autowired
    private RestFB restFb;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login-facebook")
    public String loginFacebook(HttpServletRequest request, Model model) throws ClientProtocolException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login?facebook=error";
        }
        String accessToken = restFb.getToken(code);

        com.restfb.types.User user = restFb.getUserInfo(accessToken);
        UserDetails userDetail = restFb.buildUser(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }
//    @PostMapping
//    public String login(@RequestBody AuthenticationRequest authenticationRequest) throws LoginException {
//        authService.login(authenticationRequest);
//        return "index";
//    }
}



