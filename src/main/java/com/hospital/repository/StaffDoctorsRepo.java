package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.StaffDoctors;

@Repository
public interface StaffDoctorsRepo extends JpaRepository<StaffDoctors, Integer> {

	@Query(value = "select doctor_id from staff_doctors where staff_info_id=:id"
			, nativeQuery = true)
	public List<String> getDoctorsIds(int id);
	
}
