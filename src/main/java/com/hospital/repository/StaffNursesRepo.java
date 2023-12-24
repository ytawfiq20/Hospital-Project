package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.StaffNurses;

@Repository
public interface StaffNursesRepo extends JpaRepository<StaffNurses, Integer> {

	@Query(value = "select nurse_id from staff_nurses where staff_info_id=:id"
			, nativeQuery = true)
	public List<String> getNursesIds(int id);
	
}
