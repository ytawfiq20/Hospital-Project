package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.entity.StaffInfo;

@Repository
public interface StaffInfoRepo extends JpaRepository<StaffInfo, Integer> {

	
	
}
