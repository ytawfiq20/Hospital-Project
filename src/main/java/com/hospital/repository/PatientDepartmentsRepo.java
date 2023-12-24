package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.PatientDepartments;

@Repository
public interface PatientDepartmentsRepo extends JpaRepository<PatientDepartments, Integer> {

	@Query(value = "select department_id from patient_departments where patient_id=:id"
			,nativeQuery = true)
	public List<String> getDepartmentsIdsByPatientId(int id);
	
}
