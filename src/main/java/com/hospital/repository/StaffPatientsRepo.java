package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.entity.StaffPatients;

@Repository
public interface StaffPatientsRepo extends JpaRepository<StaffPatients, Integer> {

}
