package com.hospital.service;


import java.util.Optional;


import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Doctor;
import com.hospital.repository.DoctorRepo;

@Service
public class DoctorService {
    
    DoctorRepo doctorRepo;

    public DoctorService(DoctorRepo doctorRepo){
        this.doctorRepo = doctorRepo;
    }

    public void saveDoctor(Doctor doctor){
        doctorRepo.save(doctor);
    }

    public void updateDoctor(Doctor doctor){
        Doctor oldDoctor = getDoctorById(doctor.getId());
        oldDoctor.setCreated_at(doctor.getCreated_at());
        oldDoctor.setDepartment(doctor.getDepartment());
        oldDoctor.setDescription(doctor.getDescription());
        oldDoctor.setExamination_price(doctor.getExamination_price());
        oldDoctor.setUpdated_at(doctor.getUpdated_at());
        oldDoctor.setWorking_days(doctor.getWorking_days());
        oldDoctor.setWorking_hours(doctor.getWorking_hours());
        doctorRepo.save(oldDoctor);
    }

    public Doctor getDoctorById(int id){
        Optional<Doctor> optional = doctorRepo.findById(id);
        Doctor doctor = null;
        if(optional.isPresent()){
            doctor = optional.get();
        }
        return doctor;
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepo.findAll();
    }

    public void deleteDoctorById(int id){
        doctorRepo.deleteById(id);
    }
    
    public List<Integer> getDoctorsIds(){
    	return doctorRepo.getDoctorsIds();
    }
    
    public List<String> getAllDoctorsDepartments() {
    	return doctorRepo.getAll();
    }
    
    public List<String> getDoctorsIdsByDepartmentId(int id){
    	return doctorRepo.getDoctorsIdsByDepId(id);
    }
    
    public List<Doctor> getAllDoctorsByDepartmentId(int id){
    	return doctorRepo.getAllDoctorsByDepartmentId(id);
    }
   
    public List<Doctor> getDoctorsForPatientSessionByPatientId(int patient_id){
    	return doctorRepo.getDoctorsForPatientSessionByPatientId(patient_id);
    }
    
    public List<Doctor> getByName(String name){
    	return doctorRepo.getByName(name);
    }
    
    public List<Doctor> getByNameLike(String name){
    	return doctorRepo.getByNameLike(name);
    }

}
