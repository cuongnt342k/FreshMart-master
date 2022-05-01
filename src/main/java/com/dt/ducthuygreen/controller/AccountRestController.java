package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.UserUpdateDTO;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    @Autowired
    IUserService userService;

    @PutMapping("/edit")
    @ResponseBody
    public String editProductById(@Valid @RequestBody UserUpdateDTO userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

    @PutMapping("/change-status/{id}")
    public User changeStatusAccount(@PathVariable("id") Long id) {
        return null;
    }
}
