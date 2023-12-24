package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.PatientDepartments;
import com.hospital.repository.PatientDepartmentsRepo;

@Service
public class PatientDepartmentService {

	private PatientDepartmentsRepo patientDepartmentsRepo;
	
	public PatientDepartmentService(PatientDepartmentsRepo patientDepartmentsRepo) {
		this.patientDepartmentsRepo = patientDepartmentsRepo;
	}
	
	public void savePatientDepartments(PatientDepartments patientDepartments) {
		patientDepartmentsRepo.save(patientDepartments);
	}
	
	public void delete(int id) {
		patientDepartmentsRepo.deleteById(id);
	}
	
	public List<PatientDepartments> getAllPatientDepartments(){
		return patientDepartmentsRepo.findAll();
	}
	
	public PatientDepartments getById(int id) {
		Optional<PatientDepartments> optional = patientDepartmentsRepo.findById(id);
		PatientDepartments patientDepartments = null;
		if(optional.isPresent()) {
			patientDepartments = optional.get();
		}
		return patientDepartments;
	}
	
	public void update(PatientDepartments patientDepartments) {
		PatientDepartments old = getById(patientDepartments.getId());
		old.setDepartment(patientDepartments.getDepartment());
		old.setPatient(patientDepartments.getPatient());
		patientDepartmentsRepo.save(old);
	}
	
	public List<String> getDepartmentsIdsByPatientId(int id){
		return patientDepartmentsRepo.getDepartmentsIdsByPatientId(id);
	}
	
}
