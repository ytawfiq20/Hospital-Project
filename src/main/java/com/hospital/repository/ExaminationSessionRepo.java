package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.ExaminationSession;

@Repository
public interface ExaminationSessionRepo 
	extends JpaRepository<ExaminationSession, Integer> {

	@Query(value = "select doctor_id from examination_session where patient_id=:patient_id"
			, nativeQuery = true)
	public List<String> getDoctorsIdsByPatientId(int patient_id);
	
	@Query(value = "select patient_id from examination_session where doctor_id=:doctor_id"
			, nativeQuery = true)
	public List<String> getPatientIdsByDoctorId(int doctor_id);
	
	@Query(value = "select * from examination_session where patient_id=:patient_id"
			, nativeQuery = true)
	public List<ExaminationSession> getAllByPatientId(int patient_id);
}
