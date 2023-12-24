package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    
	@Query(value = "select name from department where id=:id", nativeQuery = true)
	public String getDepartmentNameById(int id);
}
