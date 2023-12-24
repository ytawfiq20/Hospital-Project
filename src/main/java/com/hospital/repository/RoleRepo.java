package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Role;

import jakarta.transaction.Transactional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

	@Query(value = "select * from role where person_id=:person_id", nativeQuery = true)
	public List<Role> getPersonRolesByPersonId(int person_id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from role where id=:id", nativeQuery = true)
	public void deleteRoleById(int id);

	
}


