package com.dt.ducthuygreen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dt.ducthuygreen.entities.Role;
import com.dt.ducthuygreen.repos.RoleRepository;

@SpringBootApplication
public class DucThuyGreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DucThuyGreenApplication.class, args);
    }

    @Autowired
    RoleRepository roleRepository;
    
    @Bean
    CommandLineRunner init() {
        return (args) -> {
            if(roleRepository.count() == 0) {
            	roleRepository.save(new Role("ROLE_MEMBER"));
            	roleRepository.save(new Role("ROLE_ADMIN"));
            }
        };
    }
    
}
