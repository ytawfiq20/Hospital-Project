package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.entity.PatientExaminationDate;
import com.hospital.repository.PatientExaminationDateRepo;

@Service
public class PatientExaminationDateService {

	@Autowired
	private PatientExaminationDateRepo patientExaminationDateRepo;
	
	public PatientExaminationDateService
			(PatientExaminationDateRepo patientExaminationDateRepo) {
		this.patientExaminationDateRepo = patientExaminationDateRepo;
	}
	
	public void saveExaminationDate(PatientExaminationDate patientExaminationDate) {
		patientExaminationDateRepo.save(patientExaminationDate);
	}
	
	public List<PatientExaminationDate> getAllDates(){
		return patientExaminationDateRepo.findAll();
	}
	
	public void deleteDateById(int id) {
		patientExaminationDateRepo.deleteById(id);
	}
	
	public PatientExaminationDate getExaminationDateById(int id) {
		Optional<PatientExaminationDate> optional = patientExaminationDateRepo.findById(id);
		PatientExaminationDate patientExaminationDate = null;
		if(optional.isPresent()) {
			patientExaminationDate = optional.get();
		}
		return patientExaminationDate;
	}
	
	public void updateExaminationDate(PatientExaminationDate p) {
		PatientExaminationDate oldDate = getExaminationDateById(p.getId());
		oldDate.setDate(p.getDate());
		oldDate.setPatient(p.getPatient());
		oldDate.setCreated_at(p.getCreated_at());
		oldDate.setId(p.getId());
		oldDate.setUpdated_at(p.getUpdated_at());
		patientExaminationDateRepo.save(oldDate);
	}
	
	public List<String> getPatientDatesAndExaminationIdsByPatientId(int id){
		return patientExaminationDateRepo.getPatientDatesAndExaminationIdsByPatientId(id);
	}
	
	public List<String> getPatientDatesById(int patient_id){
		return patientExaminationDateRepo.getExaminationDates(patient_id);
	}
	
	public int getPatientIdByExaminationDateId(int id) {
		return patientExaminationDateRepo.getPatientId(id);
	}
	
}
