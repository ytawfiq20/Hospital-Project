package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Worker;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Integer> {
	@Query(value = "select worker.* from worker inner join employee "
			+ "on replace(concat(first_name,last_name),' ','')=:name"
			, nativeQuery = true)
	public List<Worker> getByName(String name);
	
	@Query(value = " select worker.* from worker inner join employee "
			+ "on replace(concat(employee.first_name,employee.last_name),' ','')"
			+ "LIKE concat('%',:name,'%') and job_title='Worker'"
			, nativeQuery = true)
	public List<Worker> getByNameLike(String name);
}
