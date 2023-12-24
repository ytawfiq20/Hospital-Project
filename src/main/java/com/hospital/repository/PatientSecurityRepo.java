package com.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.PatientSecurity;

@Repository
public interface PatientSecurityRepo extends JpaRepository<PatientSecurity, Integer> {

	@Query(value = "select * from patient_security where username=:username"
			, nativeQuery = true)
	public Optional<PatientSecurity> findByUsername(String username);
	
}
