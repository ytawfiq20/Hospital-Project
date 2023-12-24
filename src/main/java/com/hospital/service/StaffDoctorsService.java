package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.StaffDoctors;
import com.hospital.repository.StaffDoctorsRepo;

@Service
public class StaffDoctorsService {

	private StaffDoctorsRepo staffDoctorsRepo;
	
	public StaffDoctorsService(StaffDoctorsRepo staffDoctorsRepo) {
		this.staffDoctorsRepo=staffDoctorsRepo;
	}
	
	public void addStaffDoctor(StaffDoctors staffDoctors) {
		staffDoctorsRepo.save(staffDoctors);
	}
	
	public void deleteById(int id) {
		staffDoctorsRepo.deleteById(id);
	}
	
	public List<StaffDoctors> getAll(){
		return staffDoctorsRepo.findAll();
	}
	
	public StaffDoctors getById(int id) {
		Optional<StaffDoctors> optional = staffDoctorsRepo.findById(id);
		StaffDoctors staffDoctors = null;
		if(optional.isPresent()) {
			staffDoctors = optional.get();
		}
		return staffDoctors;
	}
	
	public void update(StaffDoctors staffDoctors) {
		StaffDoctors old = getById(staffDoctors.getId());
		old.setDoctor(staffDoctors.getDoctor());
		old.setStaffInfo(staffDoctors.getStaffInfo());
		old.setId(staffDoctors.getId());
		staffDoctorsRepo.save(old);
	}
	
	public List<String> getDoctorsIds(int id){
		return staffDoctorsRepo.getDoctorsIds(id);
	}
	
}
