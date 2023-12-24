package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.StaffInfo;
import com.hospital.repository.StaffInfoRepo;

@Service
public class StaffInfoService {

	private StaffInfoRepo staffInfoRepo;
	
	private StaffInfoService(StaffInfoRepo staffInfoRepo) {
		this.staffInfoRepo=staffInfoRepo;
	}
	
	public void addStaffInfo(StaffInfo staffInfo) {
		staffInfoRepo.save(staffInfo);
	}
	
	public void deleteStaffInfoById(int id) {
		staffInfoRepo.deleteById(id);
	}
	
	public List<StaffInfo> getAllStaffsInfo(){
		return staffInfoRepo.findAll();
	}
	
	public StaffInfo getStaffInfoById(int id) {
		Optional<StaffInfo> optional = staffInfoRepo.findById(id);
		StaffInfo staffInfo = null;
		if(optional.isPresent()) {
			staffInfo=optional.get();
		}
		return staffInfo;
	}
	
	public void updateStaffInfo(StaffInfo staffInfo) {
		StaffInfo old = getStaffInfoById(staffInfo.getId());
		old.setDoctors(staffInfo.getDoctors());
		old.setNurses(staffInfo.getNurses());
		old.setPatients(staffInfo.getPatients());
		old.setStaff(staffInfo.getStaff());
		old.setId(staffInfo.getId());
		staffInfoRepo.save(old);
	}
	
}
