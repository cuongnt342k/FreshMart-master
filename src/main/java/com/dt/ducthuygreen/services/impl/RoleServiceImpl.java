package com.dt.ducthuygreen.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.dto.RoleDTO;
import com.dt.ducthuygreen.entities.Role;
import com.dt.ducthuygreen.repos.RoleRepository;
import com.dt.ducthuygreen.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getRoleByName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public Role createNewRole(RoleDTO roleDTO) {
		return roleRepository.save(new Role(roleDTO.getRoleName()));
	}

}
