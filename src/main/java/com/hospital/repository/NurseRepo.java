package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Nurse;

@Repository
public interface NurseRepo extends JpaRepository<Nurse, Integer> {
	
	@Query(value = "select nurse.* from nurse inner join employee "
			+ "on replace(concat(first_name,last_name),' ','')=:name"
			, nativeQuery = true)
	public List<Nurse> getByName(String name);
	
	@Query(value = " select nurse.* from nurse inner join employee "
			+ "on replace(concat(employee.first_name,employee.last_name),' ','')"
			+ "LIKE concat('%',:name,'%') and job_title='Nurse'"
			, nativeQuery = true)
	public List<Nurse> getByNameLike(String name);

}
