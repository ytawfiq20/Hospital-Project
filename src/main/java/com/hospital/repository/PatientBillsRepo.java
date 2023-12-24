package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.PatientBills;

@Repository
public interface PatientBillsRepo extends JpaRepository<PatientBills, Integer> {
    
    @Query(value = "select * from patient_bills where patient_id=:id", nativeQuery = true)
    public List<PatientBills> getPatientBillsById(int id);

}
