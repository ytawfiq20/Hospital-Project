package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.PatientStatus;

@Repository
public interface PatientStatusRepo extends JpaRepository<PatientStatus, Integer> {
    
    @Query(value = "select * from patient_status where patient_id=:id", nativeQuery = true)
	public List<PatientStatus> getAllPatientStatusByPatientId(int id);
    
    @Query(value = "select patient_id from patient_status where id=:id"
    		, nativeQuery = true)
    public int getPatientIdByStatusId(int id);

}
