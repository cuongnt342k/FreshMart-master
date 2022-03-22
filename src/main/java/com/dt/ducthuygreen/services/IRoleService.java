package com.dt.ducthuygreen.services;

import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.RoleDTO;
import com.dt.ducthuygreen.entities.Role;

public interface IRoleService {
	Role getRoleByName(String roleName);
	Role createNewRole(RoleDTO roleDTO);
}
