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
import com.hospital.entity.Nurse;
import com.hospital.service.EmployeeService;
import com.hospital.service.NurseService;

@Controller
@RequestMapping("/nurse")
public class NurseController {

	@Autowired
	NurseService nurseService;
	@Autowired
	EmployeeService employeeService;
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allNurses")
	public String getAllNurses(Model model) {
		model.addAttribute("nurses", nurseService.getAllNurses());
		return "all-nurses";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addNurse/{id}")
	public String addNurseForm(@PathVariable int id, Model model) {
		model.addAttribute("emp_id", id);
		return "add-nurse";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addNurse")
	public String saveNurse(Model model,
			@RequestParam int id, 
			@ModelAttribute Nurse nurse) {
		
		
		Employee employeeDetails = employeeService.getEmployeeById(id);
		
		Nurse newNurse = new Nurse(id, nurse.getWorking_days(), nurse.getWorking_hours()
				, employeeDetails);
		
		if(nurse.getWorking_days()==null){
			model.addAttribute("Error", "Choose Working days");
			return "add-nurse";
		}
		if(nurse.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			return "add-nurse";
		}
		
		nurseService.saveNurse(newNurse);
		employeeDetails.setIs_employed((short) 1);
		employeeDetails.setJob_title("Nurse");
		employeeService.updateEmployee(employeeDetails);
		return "redirect:/nurse/allNurses";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateNurse/{id}")
	public String updateNurseForm(@PathVariable int id, Model model) {
		model.addAttribute("nurse", nurseService.getNurseById(id));
		return "update-nurse";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateNurse")
	public String saveUpdatesToNurse(Model model,
			@ModelAttribute Nurse nurse) {
		
		//System.out.println(nurse);
		
		if(nurse.getWorking_days()==null){
			model.addAttribute("Error", "Choose Working days");
			model.addAttribute("nurse", nurseService.getNurseById(nurse.getId()));
			return "update-nurse";
		}
		if(nurse.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			model.addAttribute("nurse", nurseService.getNurseById(nurse.getId()));
			return "update-nurse";
		}
		
		nurseService.updateNurse(nurse);
		return "redirect:/nurse/allNurses";
		
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteNurse/{id}")
	public String deleteNurse(@PathVariable int id) {
		nurseService.deleteNurseById(id);
		return "redirect:/nurse/allNurses";
	}
	
    @GetMapping("/search")
    public String search(Model model, @RequestParam String name) {
    	String name1 = removeSpaces(name);
    	model.addAttribute("nurses1", nurseService.getByName(name1));
    	model.addAttribute("nurses", nurseService.getAllNurses());
    	return "all-nurses";
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
