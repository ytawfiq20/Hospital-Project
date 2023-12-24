package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.StaffNurses;
import com.hospital.repository.StaffNursesRepo;

@Service
public class StaffNursesService {

	private StaffNursesRepo staffNursesRepo;
	
	public StaffNursesService(StaffNursesRepo staffNursesRepo) {
		this.staffNursesRepo = staffNursesRepo;
	}
	
	public void add(StaffNurses staffNurses) {
		staffNursesRepo.save(staffNurses);
	}
	
	public void deleteById(int id) {
		staffNursesRepo.deleteById(id);
	}
	
	public List<StaffNurses> getAll(){
		return staffNursesRepo.findAll();
	}
	
	public StaffNurses getById(int id) {
		Optional<StaffNurses> optional = staffNursesRepo.findById(id);
		StaffNurses staffNurses = null;
		if(optional.isPresent()) {
			staffNurses = optional.get();
		}
		return staffNurses;
	}
	
	public void update(StaffNurses staffNurses) {
		StaffNurses old = getById(staffNurses.getId());
		old.setId(staffNurses.getId());
		old.setNurse(staffNurses.getNurse());
		old.setStaffInfo(staffNurses.getStaffInfo());
		staffNursesRepo.save(old);
	}
	
	public List<String> getNursesIds(int id){
		return staffNursesRepo.getNursesIds(id);
	}
	
}
