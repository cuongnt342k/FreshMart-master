package com.dt.ducthuygreen.dto;

import javax.persistence.Column;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupDTO {

	private String userName;

    private String password;

    private String email;

    private String fullName;
    
    private String roles;
}
