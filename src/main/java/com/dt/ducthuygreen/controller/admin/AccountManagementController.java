package com.dt.ducthuygreen.controller.admin;

import com.dt.ducthuygreen.entities.Role;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.repos.RoleRepository;
import com.dt.ducthuygreen.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/account")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AccountManagementController {

    @Autowired
    RoleRepository repository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String listAccountPage() {
        return "/management/list-account";
    }
    @GetMapping("/addAccount")
    public String addAccountPage(Model model) {
        List<Role> roles = repository.findAll();
        model.addAttribute("roles",roles);
        return "/management/add-account-form";
    }

    @GetMapping("/editAccount/{id}")
    public String editAccountPage(Model model, @PathVariable("id") Long id) {
        List<Role> roles = repository.findAll();
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        model.addAttribute("roles",roles);
        return "/management/edit-account-form";
    }

}
