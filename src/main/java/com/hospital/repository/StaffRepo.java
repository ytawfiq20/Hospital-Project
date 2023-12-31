package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer> {

}
