package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Manager;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, Integer> {
	@Query(value = "select manager.* from manager inner join employee "
			+ "on replace(concat(first_name,last_name),' ','')=:name"
			, nativeQuery = true)
	public List<Manager> getByName(String name);
	
	@Query(value = " select manager.* from manager inner join employee "
			+ "on replace(concat(employee.first_name,employee.last_name),' ','')"
			+ "LIKE concat('%',:name,'%') and job_title='Manager'"
			, nativeQuery = true)
	public List<Manager> getByNameLike(String name);
}
