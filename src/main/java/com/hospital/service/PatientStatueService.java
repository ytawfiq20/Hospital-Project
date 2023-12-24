package com.hospital.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.PatientStatus;
import com.hospital.repository.PatientStatusRepo;

@Service
public class PatientStatueService {

    private PatientStatusRepo patientStatusRepo;
	
	public PatientStatueService(PatientStatusRepo patientStatusRepo) {
		this.patientStatusRepo = patientStatusRepo;
	}
	
	public void addStatus(PatientStatus patientStatus) {
		patientStatusRepo.save(patientStatus);
	}
	
	public void deleteStatusById(int id) {
		patientStatusRepo.deleteById(id);
	}
	
	public PatientStatus getById(int id) {
		Optional<PatientStatus> optional = patientStatusRepo.findById(id);
		PatientStatus patientCondition = null;
		if(optional.isPresent()) {
			patientCondition = optional.get();
		}
		return patientCondition;
	}
	
	public List<PatientStatus> getAllStatus(){
		return patientStatusRepo.findAll();
	}
	
	public void update(PatientStatus patientStatus) {
		PatientStatus old = getById(patientStatus.getId());
		old.setState(patientStatus.getState());
		old.setMedicine(patientStatus.getMedicine());
		old.setPatient(patientStatus.getPatient());
		old.setRe_examination_date(patientStatus.getRe_examination_date());
		patientStatusRepo.save(old);
	}
	
	public List<PatientStatus> getAllPatientStatusByPatientId(int id){
		return patientStatusRepo.getAllPatientStatusByPatientId(id);
	}
	
	public int getPatientIdByStatusId(int status_id) {
		return patientStatusRepo.getPatientIdByStatusId(status_id);
	}
    
}
