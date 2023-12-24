package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.service.DoctorService;
import com.hospital.service.EmployeeService;
import com.hospital.service.ManagerService;
import com.hospital.service.NurseService;
import com.hospital.service.PatientService;
import com.hospital.service.PersonSecurityService;
import com.hospital.service.WorkerService;

@Controller
@RequestMapping("/search")
@Secured({"ADMIN", "NURSE"})
public class SearchController {
	
	@Autowired
	DoctorService doctorService;
	@Autowired
	NurseService nurseService;
	@Autowired
	ManagerService managerService;
	@Autowired
	WorkerService workerService;
	@Autowired
	PersonSecurityService personSecurityService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	PatientService patientService;

	@GetMapping
	public String search(@RequestParam String search, @RequestParam String id, Model model) {
		int id1 = Integer.parseInt(id);
		if (search.isBlank()) {
			return emptySearchForm(id1);
		}else {
			switch(id1) {
				case 1:{
					return getDoctor(search, model);
				}
				case 2:{
					return getNurse(search, model);
				}
				case 3:{
					return getManager(search, model);
				}
				case 4:{
					return getWorker(search, model);
				}
				case 5:{
					return getPatient(search, model);
				}
				case 6:{
					return getPerson(search, model);
				}
				case 7:{
					return getEmployee(search, model);
				}
			}
		}
		return "home-page";
	}
	
	public String emptySearchForm(int id) {
		switch (id) {
		case 1: {
			return "redirect:/doctor/allDoctors";
		}
		case 2: {
			return "redirect:/nurse/allNurses";
		}
		case 3: {
			return "redirect:/manager/allManagers";
		}
		case 4: {
			return "redirect:/worker/allWorkers";
		}
		case 5: {
			return "redirect:/patient/allPatients";
		}
		case 6: {
			return "redirect:/admin/allPersons";
		}
	}
		return "redirect:/employee/allEmployees";
	}
	
	public String getDoctor(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("doctors1", doctorService.getByNameLike(name));
		model.addAttribute("doctors", doctorService.getAllDoctors());
		return "all-doctors";
	}
	
	public String getNurse(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("nurses1", nurseService.getByNameLike(name));
		model.addAttribute("nurses", nurseService.getAllNurses());
		return "all-nurses";
	}
	
	public String getManager(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("managers1", managerService.getByNameLike(name));
		model.addAttribute("managers", managerService.getAllManagers());
		return "all-managers";
	}
	
	public String getWorker(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("workers1", workerService.getByNameLike(name));
		model.addAttribute("workers", workerService.getAllWorkers());
		return "all-workers";
	}
	
	public String getPatient(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("patients1", patientService.getByNameLike(name));
		model.addAttribute("patients", patientService.getAllPatients());
		return "all-patients";
	}
	
	public String getEmployee(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("employees1", employeeService.getByNameLike(name));
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "all-employees";
	}
	
	public String getPerson(String search, Model model) {
		String name = removeSpaces(search);
		model.addAttribute("persons1", personSecurityService.getByNameLike(name));
		model.addAttribute("persons", personSecurityService.getAll());
		return "all-auth-persons";
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
