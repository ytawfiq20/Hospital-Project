package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.hospital.entity.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

	@Query(value = "select exists(select phone_number from patient where phone_number="
			+ ":phone_number)", nativeQuery = true)
	public int isPhoneNumberExists(String phone_number);
	
	@Query(value = "select exists(select email from patient where email="
			+ ":email)", nativeQuery = true)
	public int isEmailExists(String email);
	
	@Query(value = "select * from patient"
			+ " where replace(concat(first_name,last_name),' ','')=:name", nativeQuery = true)
	public List<Patient> getByName(String name);
	
	@Query(value = "select * from patient "
    		+ "where replace(concat(first_name,last_name),' ','')"
    		+ "LIKE CONCAT('%',:name,'%') group by id", nativeQuery = true)
    public List<Patient> getByNameLike(String name);
}
