package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.BaseModel;
import com.dt.ducthuygreen.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO extends BaseModel{
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String fullName;

    private String description;

    private Integer status;

    private Set<Role> roles;

}
