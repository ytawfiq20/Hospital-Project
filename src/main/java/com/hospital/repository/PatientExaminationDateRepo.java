package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.PatientExaminationDate;

@Repository
public interface PatientExaminationDateRepo 
	extends JpaRepository<PatientExaminationDate, Integer> {

	@Query(value = "select id, date from examination_date where patient_id=:patient_id"
			, nativeQuery = true)
	public List<String> getPatientDatesAndExaminationIdsByPatientId(int patient_id);
	
	@Query(value = "select date from examination_date where patient_id=:patient_id"
			, nativeQuery = true)
	public List<String> getExaminationDates(int patient_id);
	
	@Query(value = "select patient_id from examination_date where id=:id"
			, nativeQuery = true)
	public int getPatientId(int id);
	
	
	
}
