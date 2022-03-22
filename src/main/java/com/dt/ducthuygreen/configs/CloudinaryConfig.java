package com.dt.ducthuygreen.configs;

import com.cloudinary.Cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary config() {
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name", "ha-noi-university-of-industry-vip");
        config.put("api_key", "949468279372728");
        config.put("api_secret", "zEXj4SM29BVIlInlmebfEpsB4YU");
        return new Cloudinary(config);
    }

}
