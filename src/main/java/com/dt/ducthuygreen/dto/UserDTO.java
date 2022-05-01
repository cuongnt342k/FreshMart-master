package com.dt.ducthuygreen.dto;

import java.util.Set;

import com.dt.ducthuygreen.entities.BaseModel;
import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseModel {

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

	private String confirmPassword;

    private Integer status;

    private Set<Role> roles;

	private Cart cart = new Cart();


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    
    

}
