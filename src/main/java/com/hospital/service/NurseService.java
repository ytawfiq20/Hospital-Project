package com.hospital.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Nurse;
import com.hospital.repository.NurseRepo;

@Service
public class NurseService {

	private NurseRepo nurseRepo;
	
	public NurseService(NurseRepo nurseRepo) {
		this.nurseRepo = nurseRepo;
	}
	
	public void saveNurse(Nurse nurse) {
		nurseRepo.save(nurse);
	}
	
	public void deleteNurseById(int id) {
		nurseRepo.deleteById(id);
	}
	
	public Nurse getNurseById(int id) {
		Optional<Nurse> optional = nurseRepo.findById(id);
		Nurse nurse = null;
		if(optional.isPresent()) {
			nurse = optional.get();
		}
		return nurse;
	}
	
	public List<Nurse> getAllNurses(){
		return nurseRepo.findAll();
	}
	
	public void updateNurse(Nurse nurse) {
		Nurse oldNurse = getNurseById(nurse.getId());
		oldNurse.setWorking_days(nurse.getWorking_days());
		oldNurse.setWorking_hours(nurse.getWorking_hours());
		oldNurse.setCreated_at(nurse.getCreated_at());
		oldNurse.setEmployee(nurse.getEmployee());
		oldNurse.setUpdated_at(nurse.getUpdated_at());
		nurseRepo.save(oldNurse);
	}
	
	public List<Nurse> getByName(String name){
		return nurseRepo.getByName(name);
	}
	
    public List<Nurse> getByNameLike(String name){
    	return nurseRepo.getByNameLike(name);
    }
	
}
