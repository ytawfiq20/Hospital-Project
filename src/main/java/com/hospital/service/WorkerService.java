package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital.entity.Worker;
import com.hospital.repository.WorkerRepo;

@Service
public class WorkerService {

	private WorkerRepo workerRepo;
	
	public WorkerService(WorkerRepo workerRepo) {
		this.workerRepo = workerRepo;
	}
	
	public void saveWorker(Worker worker) {
		workerRepo.save(worker);
	}
	
	public void deleteWorkerById(int id) {
		workerRepo.deleteById(id);
	}
	
	public List<Worker> getAllWorkers(){
		return workerRepo.findAll();
	}
	
	public Worker getWorkerById(int id){
		Optional<Worker> optional = workerRepo.findById(id);
		Worker worker = null;
		if(optional.isPresent()) {
			worker = optional.get();
		}
		return worker;
	}
	
	public void updateWorker(Worker worker) {
		Worker oldWorker = getWorkerById(worker.getId());
		oldWorker.setEmployee(worker.getEmployee());
		oldWorker.setId(worker.getId());
		oldWorker.setWorking_days(worker.getWorking_days());
		oldWorker.setWorking_hours(worker.getWorking_hours());
		workerRepo.save(oldWorker);
	}
	
	public List<Worker> getByName(String name){
		return workerRepo.getByName(name);
	}
	
    public List<Worker> getByNameLike(String name){
    	return workerRepo.getByNameLike(name);
    }
	
}
