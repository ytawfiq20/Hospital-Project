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
import com.hospital.entity.Manager;
import com.hospital.service.EmployeeService;
import com.hospital.service.ManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	ManagerService managerService;
	@Autowired
	EmployeeService employeeService;
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allManagers")
	public String getAllManagers(Model model) {
		model.addAttribute("managers", managerService.getAllManagers());
		return "all-managers";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addManager/{id}")
	public String addManagerForm(@PathVariable int id, Model model) {
		model.addAttribute("emp_id", id);
		return "add-manager";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addManager")
	public String saveManager(@RequestParam int id, 
			@ModelAttribute Manager manager, Model model) {
		
		Employee employeeDetails = employeeService.getEmployeeById(id);
		System.out.println(employeeDetails);
		
		Manager newManager = new Manager(id, manager.getWorking_days()
				, manager.getWorking_hours(), employeeDetails);
		
		if(manager.getWorking_days()==null) {
			model.addAttribute("Error", "Choose Working Days");
			model.addAttribute("emp_id", manager.getId());
			return "add-manager";
		}
		if(manager.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			model.addAttribute("emp_id", manager.getId());
			return "add-manager";
		}
		
		managerService.saveManager(newManager);
		employeeDetails.setIs_employed((short) 1);
		employeeDetails.setJob_title("Manager");
		employeeService.updateEmployee(employeeDetails);
		return "redirect:/manager/allManagers";
		
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateManager/{id}")
	public String updateManagerForm(Model model, @PathVariable int id) {
		model.addAttribute("manager", managerService.getManagerById(id));
		return "update-manager";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateManager")
	public String saveUpdatesToManager(@ModelAttribute Manager manager, Model model) {
		
		if(manager.getWorking_days()==null) {
			model.addAttribute("Error", "Choose Working Days");
			model.addAttribute("manager", managerService.getManagerById(manager.getId()));
			return "update-manager";
		}
		if(manager.getWorking_hours().length()==0) {
			model.addAttribute("Error", "Enter Working Hours");
			model.addAttribute("manager", managerService.getManagerById(manager.getId()));
			return "update-manager";
		}
		
		managerService.updateManager(manager);
		return "redirect:/manager/allManagers";
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteManager/{id}")
	public String deleteManager(@PathVariable int id) {
		managerService.deleteManagerById(id);
		return "redirect:/manager/allManagers";
	}
	
	@Secured({"ADMIN","NURSE"})
    @GetMapping("/search")
    public String search(Model model, @RequestParam String name) {
    	String name1 = removeSpaces(name);
    	model.addAttribute("managers1", managerService.getByName(name1));
    	model.addAttribute("managers", managerService.getAllManagers());
    	return "all-managers";
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
