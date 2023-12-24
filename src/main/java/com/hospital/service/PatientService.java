package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepo;

@Service
public class PatientService {

	private PatientRepo patientRepo;
	
	public PatientService(PatientRepo patientRepo) {
		this.patientRepo = patientRepo;
	}
	
	public void addPatient(Patient patient) {
		patientRepo.save(patient);
	}
	
	public Patient getPatientById(int id) {
		Optional<Patient> optional = patientRepo.findById(id);
		Patient patient = null;
		if(optional.isPresent()) {
			patient = optional.get();
		}
		return patient;
	}
	
	public List<Patient> getAllPatients(){
		return patientRepo.findAll();
	}
	
	public void deletePatientById(int id) {
		patientRepo.deleteById(id);
	}
	
	public void updatePatient(Patient patient) {
		Patient oldPatient = getPatientById(patient.getId());
		oldPatient.setCreated_at(patient.getCreated_at());
		oldPatient.setEmail(patient.getEmail());
		oldPatient.setFirst_name(patient.getFirst_name());
		oldPatient.setLast_name(patient.getLast_name());
		oldPatient.setPhone_number(patient.getPhone_number());
		oldPatient.setUpdated_at(patient.getUpdated_at());
		patientRepo.save(oldPatient);
	}
	
	public boolean isPhoneNumberExists(String phone_number) {
		return patientRepo.isPhoneNumberExists(phone_number)==1;
	}
	
	public boolean isEmailExists(String email) {
		return patientRepo.isEmailExists(email)==1;
	}
	
	public List<Patient> getByName(String name) {
		return patientRepo.getByName(name);
	}
	
	public List<Patient> getByNameLike(String name) {
		return patientRepo.getByNameLike(name);
	}
	
}
