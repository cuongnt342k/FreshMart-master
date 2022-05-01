package com.dt.ducthuygreen.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dt.ducthuygreen.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRoleName(String roleName);

	Role findRoleById(Long id);
}
