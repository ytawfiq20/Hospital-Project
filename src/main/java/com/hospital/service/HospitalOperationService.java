package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.HospitalOperations;
import com.hospital.repository.HospitalOperationsRepo;


@Service
public class HospitalOperationService {

	private HospitalOperationsRepo hospitalOperationsRepo;
	
	public HospitalOperationService(HospitalOperationsRepo hospitalOperationsRepo) {
		this.hospitalOperationsRepo = hospitalOperationsRepo;
	}
	
	public void addOperation(HospitalOperations hospitalOperations) {
		hospitalOperationsRepo.save(hospitalOperations);
	}
	
	public void deleteOperationById(int id) {
		hospitalOperationsRepo.deleteById(id);
	}
	
	public List<HospitalOperations> getAllOperations(){
		return hospitalOperationsRepo.findAll();
	}
	
	public HospitalOperations getOperationById(int id) {
		Optional<HospitalOperations> optional = hospitalOperationsRepo.findById(id);
		HospitalOperations operation = null;
		if(optional.isPresent()) {
			operation = optional.get();
		}
		return operation;
	}
	
	public void updateOperation(HospitalOperations hospitalOperations) {
		HospitalOperations oldOperation = getOperationById(hospitalOperations.getId());
		oldOperation.setName(hospitalOperations.getName());
		hospitalOperationsRepo.save(oldOperation);
	}
	
}
