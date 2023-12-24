package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.entity.HospitalOperations;

@Repository
public interface HospitalOperationsRepo extends 
	JpaRepository<HospitalOperations, Integer> {

}
