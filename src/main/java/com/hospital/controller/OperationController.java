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

import com.hospital.entity.HospitalOperations;
import com.hospital.service.HospitalOperationService;

@Controller
@RequestMapping("/operation")
public class OperationController {
	
	@Autowired
	HospitalOperationService hospitalOperationService;
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addOperation")
	public String addOperationForm() {
		return "add-operation";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addOperation")
	public String saveOperationName(@RequestParam String name, Model model) {
		if(name.length()==0) {
			model.addAttribute("Error", "Enter operation name");
			return "add-operation";
		}
		hospitalOperationService.addOperation(new HospitalOperations(name));
		return "redirect:/operation/allOperations";
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteOperation/{id}")
	public String deleteOperationById(@PathVariable int id, Model model) {
		hospitalOperationService.deleteOperationById(id);
		return "redirect:/operation/allOperations";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateOperation/{id}")
	public String updateOperation(@PathVariable int id, Model model) {
		model.addAttribute("operation", hospitalOperationService.getOperationById(id));
		return "update-operation";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateOperation")
	public String saveUpdatesToOperation(@ModelAttribute HospitalOperations operation
			, Model model) {
		if(operation.getName().length()==0) {
			model.addAttribute("Error", "Enter operation name");
			model.addAttribute("operation", 
					hospitalOperationService.getOperationById(operation.getId()));
			return "update-operation";
		}
		hospitalOperationService.updateOperation(operation);
		return "redirect:/operation/allOperations";
	}

	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allOperations")
	public String getAllOperations(Model model) {
		model.addAttribute("ops", hospitalOperationService.getAllOperations());
		return "all-operations";
	}
}
