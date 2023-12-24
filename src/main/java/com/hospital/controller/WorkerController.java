package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.entity.Employee;
import com.hospital.entity.Worker;
import com.hospital.service.EmployeeService;
import com.hospital.service.WorkerService;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	WorkerService workerService;
	@Autowired
	EmployeeService employeeService;
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allWorkers")
	public String getAllWorkers(Model model) {
		model.addAttribute("workers", workerService.getAllWorkers());
		return "all-workers";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addWorker/{id}")
	public String addWorkerForm(@PathVariable int id, Model model) {
		model.addAttribute("emp_id", id);
		return "add-worker";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addWorker")
	public String saveWorker(@RequestParam int id, 
			@ModelAttribute Worker worker, Model model) {
		
		Employee employeeDetails = employeeService.getEmployeeById(id);
		Worker newWorker = new Worker(id, worker.getWorking_days(), worker.getWorking_hours()
				, employeeDetails);
		
		if(worker.getWorking_days()==null) {
			model.addAttribute("Error", "Choose Working Days");
			model.addAttribute("emp_id", worker.getId());
			return "add-worker";
		}
		if(worker.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			model.addAttribute("emp_id", worker.getId());
			return "add-worker";
		}
		
		workerService.saveWorker(newWorker);
		employeeDetails.setIs_employed((short) 1);
		employeeDetails.setJob_title("Worker");
		employeeService.updateEmployee(employeeDetails);
		return "redirect:/worker/allWorkers";
		
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateWorker/{id}")
	public String updateWorkerForm(@PathVariable int id, Model model) {
		model.addAttribute("worker", workerService.getWorkerById(id));
		return "update-worker";
	}
	
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateWorker")
	public String saveUpdatesToWorker(@ModelAttribute Worker worker, Model model) {
		
		if(worker.getWorking_days()==null) {
			model.addAttribute("Error", "Choose Working Days");
			model.addAttribute("worker", workerService.getWorkerById(worker.getId()));
			return "update-worker";
		}
		if(worker.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			model.addAttribute("worker", workerService.getWorkerById(worker.getId()));
			return "update-worker";
		}
		
		workerService.updateWorker(worker);
		return "redirect:/worker/allWorkers";
	
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteWorker/{id}")
	public String deleteWorkerById(@PathVariable int id) {
		workerService.deleteWorkerById(id);
		return "redirect:/worker/allWorkers";
	}
	
	@Secured({"ADMIN","NURSE"})
    @GetMapping("/search")
    public String search(Model model, @RequestParam String name) {
    	String name1 = removeSpaces(name);
    	model.addAttribute("workers1", workerService.getByName(name1));
    	model.addAttribute("workers", workerService.getAllWorkers());
    	return "all-workers";
    }
    
    public String removeSpaces(String name) {
		 String ans = "";
		 for(int i=0; i<name.length(); i++) {
			 if(name.charAt(i)!=' ') {
				 ans += name.charAt(i);
			 }
		 }
		 return ans;
	 }
	
}
