package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.Utils.CustomerNotFoundException;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForgotPasswordServiceImpl {
    @Autowired
    private UserRepository customerRepo;


    public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException {
        User customer = customerRepo.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            customerRepo.save(customer);
        } else {
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return customerRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        customerRepo.save(customer);
    }
}
