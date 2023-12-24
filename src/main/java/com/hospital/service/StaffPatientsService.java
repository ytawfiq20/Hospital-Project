package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.StaffPatients;
import com.hospital.repository.StaffPatientsRepo;


@Service
public class StaffPatientsService {

	private StaffPatientsRepo staffPatientsRepo;
	
	public StaffPatientsService(StaffPatientsRepo staffPatientsRepo) {
		this.staffPatientsRepo=staffPatientsRepo;
	}
	
	public void add(StaffPatients staffPatients) {
		staffPatientsRepo.save(staffPatients);
	}
	
	public void deleteById(int id) {
		staffPatientsRepo.deleteById(id);
	}
	
	public List<StaffPatients> getAll(){
		return staffPatientsRepo.findAll();
	}
	
	public StaffPatients getById(int id) {
		Optional<StaffPatients> optional = staffPatientsRepo.findById(id);
		StaffPatients staffPatients = null;
		if(optional.isPresent()) {
			staffPatients=optional.get();
		}
		return staffPatients;
	}
	
	public void update(StaffPatients staffPatients) {
		StaffPatients old = getById(staffPatients.getId());
		old.setId(staffPatients.getId());
		old.setPatient(staffPatients.getPatient());
		old.setStaffInfo(staffPatients.getStaffInfo());
		staffPatientsRepo.save(old);
	}
}
