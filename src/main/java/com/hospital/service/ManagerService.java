package com.hospital.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Manager;
import com.hospital.repository.ManagerRepo;

@Service
public class ManagerService {

	private ManagerRepo managerRepo;
	
	public ManagerService(ManagerRepo managerRepo) {
		this.managerRepo = managerRepo;
	}
	
	public void saveManager(Manager manager) {
		managerRepo.save(manager);
	}
	
	public Manager getManagerById(int id) {
		Optional<Manager> optional = managerRepo.findById(id);
		Manager manager = null;
		if(optional.isPresent()) {
			manager = optional.get();
		}
		return manager;
	}
	
	public List<Manager> getAllManagers(){
		return managerRepo.findAll();
	}
	
	public void updateManager(Manager manager) {
		Manager oldManager = getManagerById(manager.getId());
		oldManager.setEmployee(manager.getEmployee());
		oldManager.setId(manager.getId());
		oldManager.setWorking_days(manager.getWorking_days());
		oldManager.setWorking_hours(manager.getWorking_hours());
		managerRepo.save(oldManager);
	}
	
	public void deleteManagerById(int id) {
		managerRepo.deleteById(id);
	}
	
	public List<Manager> getByName(String name){
		return managerRepo.getByName(name);
	}
	
    public List<Manager> getByNameLike(String name){
    	return managerRepo.getByNameLike(name);
    }
	
}
