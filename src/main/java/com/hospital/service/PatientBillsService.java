package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.PatientBills;
import com.hospital.repository.PatientBillsRepo;

@Service
public class PatientBillsService {
    
    private PatientBillsRepo patientBillsRepo;

    public PatientBillsService(PatientBillsRepo patientBillsRepo){
        this.patientBillsRepo = patientBillsRepo;
    }

    public void addBill(PatientBills patientBills){
        patientBillsRepo.save(patientBills);
    }

    public void deleteBillById(int id){
        patientBillsRepo.deleteById(id);
    }

    public PatientBills getBillById(int id){
        Optional<PatientBills> optional = patientBillsRepo.findById(id);
        PatientBills patientBills = null;
        if(optional.isPresent()){
            patientBills = optional.get();
        }
        return patientBills;
    }

    public List<PatientBills> getPatientBillsById(int id){
        return patientBillsRepo.getPatientBillsById(id);
    }

    public List<PatientBills> getAllBills(){
        return patientBillsRepo.findAll();
    }

}
