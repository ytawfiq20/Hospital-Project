package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.Staff;
import com.hospital.repository.StaffRepo;

@Service
public class StaffService {

	private StaffRepo staffRepo;
	
	public StaffService(StaffRepo staffRepo) {
		this.staffRepo=staffRepo;
	}
	
	public void addStaff(Staff staff) {
		staffRepo.save(staff);
	}
	
	public void deleteStaffById(int id) {
		staffRepo.deleteById(id);
	}
	
	public List<Staff> getAllStafs(){
		return staffRepo.findAll();
	}
	
	public Staff getStaffById(int id) {
		Optional<Staff> optional = staffRepo.findById(id);
		Staff staff = null;
		if(optional.isPresent()) {
			staff=optional.get();
		}
		return staff;
	}
	
	public void updateStaff(Staff staff) {
		Staff old = getStaffById(staff.getId());
		old.setHospitalOperations(staff.getHospitalOperations());
		staffRepo.save(old);
	}
	
}
