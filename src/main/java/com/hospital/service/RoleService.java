package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.Role;
import com.hospital.repository.RoleRepo;

@Service
public class RoleService {

	private RoleRepo roleRepo;
	
	public RoleService(RoleRepo roleRepo) {
		this.roleRepo=roleRepo;
	}
	
	public void addRole(Role role) {
		roleRepo.save(role);
	}
	
	public void deleteById(int id) {
		roleRepo.deleteById(id);
	}
	
	public Role getById(int id) {
		Optional<Role> optional = roleRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public List<Role> getAllRoles(){
		return roleRepo.findAll();
	}
	
	public void updateRole(Role role) {
		Role old = getById(role.getId());
		old.setName(role.getName());
		roleRepo.save(old);
	}
	
	public List<Role> getPersonRolesByPersonId(int person_id){
		return roleRepo.getPersonRolesByPersonId(person_id);
	}
}
