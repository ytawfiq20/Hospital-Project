package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.ExaminationSession;
import com.hospital.repository.ExaminationSessionRepo;

@Service
public class ExaminationSessionService {

	private ExaminationSessionRepo examinationSessionRepo;
	
	public ExaminationSessionService(ExaminationSessionRepo examinationSessionRepo) {
		this.examinationSessionRepo = examinationSessionRepo;
	}
	
	public void addExaminationSession(ExaminationSession examinationSession) {
		examinationSessionRepo.save(examinationSession);
	}
	
	public void deleteExaminationSessionById(int id) {
		examinationSessionRepo.deleteById(id);
	}
	
	public List<ExaminationSession> getAllExaminationSessions(){
		return examinationSessionRepo.findAll();
	}
	
	public ExaminationSession getExaminationSessionById(int id) {
		Optional<ExaminationSession> optional = examinationSessionRepo.findById(id);
		ExaminationSession examinationSession = null;
		if(optional.isPresent()) {
			examinationSession = optional.get();
		}
		return examinationSession;
	}
	
	public void updateExaminationSession(ExaminationSession examinationSession) {
		ExaminationSession old = getExaminationSessionById(examinationSession.getId());
		old.setDoctor(examinationSession.getDoctor());
		old.setPatient(examinationSession.getPatient());
		examinationSessionRepo.save(old);
	}
	
	public List<String> getPatientIdsByDoctorId(int doctor_id){
		return examinationSessionRepo.getPatientIdsByDoctorId(doctor_id);
	}
	
	public List<String> getDoctorsIdsByPatientId(int patient_id){
		return examinationSessionRepo.getDoctorsIdsByPatientId(patient_id);
	}
	
	public List<ExaminationSession> getAllByPatientId(int patient_id){
		return examinationSessionRepo.getAllByPatientId(patient_id);
	}
	
}
