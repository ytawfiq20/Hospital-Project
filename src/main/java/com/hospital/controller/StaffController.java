package com.hospital.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.entity.Staff;
import com.hospital.entity.StaffDoctors;
import com.hospital.entity.StaffInfo;
import com.hospital.entity.StaffNurses;
import com.hospital.service.DoctorService;
import com.hospital.service.EmployeeService;
import com.hospital.service.HospitalOperationService;
import com.hospital.service.NurseService;
import com.hospital.service.StaffDoctorsService;
import com.hospital.service.StaffInfoService;
import com.hospital.service.StaffNursesService;
import com.hospital.service.StaffService;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	StaffService staffService;
	@Autowired
	HospitalOperationService hospitalOperationService;
	@Autowired
	StaffInfoService staffInfoService;
	@Autowired
	DoctorService doctorService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	StaffDoctorsService staffDoctorsService;
	@Autowired
	StaffNursesService staffNursesService;
	@Autowired
	NurseService nurseService;
	
	/*
	 * 
	 * 
	 * deal with staff
	 * 
	 * 
	 * */
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addStaff")
	public String addStaffForm(Model model) {
		model.addAttribute("operations", hospitalOperationService.getAllOperations());
		return "add-staff";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addStaff")
	public String saveStaff(@RequestParam String operation_id, Model model) {
		int op_id = Integer.parseInt(operation_id);
		staffService.addStaff(
				new Staff(hospitalOperationService.getOperationById(op_id))
				);
		System.out.println(staffService.getAllStafs());
		return "redirect:/staff/allStaffs";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/allStaffs")
	public String getAllStaffs(Model model) {
		model.addAttribute("staffs", staffService.getAllStafs());
		return "all-staffs";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/updateStaff/{id}")
	public String updateStaff(@PathVariable int id, Model model) {
		model.addAttribute("staff", staffService.getStaffById(id));
		model.addAttribute("operations", hospitalOperationService.getAllOperations());
		return "update-staff";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/updateStaff")
	public String saveUpdatesToStaff(@RequestParam int id, @RequestParam String operation_id) {
		int op_id = Integer.parseInt(operation_id);
		Staff staff = new Staff(id, hospitalOperationService.getOperationById(op_id));
		System.out.println(staff);
		staffService.updateStaff(staff);
		return "redirect:/staff/allStaffs";
	}
	
	@Secured({"ADMIN"})
	@GetMapping("/deleteStaff/{id}")
	public String deleteStaff(@PathVariable int id) {
		staffService.deleteStaffById(id);
		return "redirect:/staff/allStaffs";
	}
	/*
	 * 
	 * 
	 * deal with staff info
	 * 
	 * 
	 * 
	 * */
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/addStaffInfo")
	public String addStaffInfoForm(Model model) {
		model.addAttribute("staffs", staffService.getAllStafs());
		return "add-staff-info";
	}
	
	@Secured({"ADMIN","NURSE"})
	@PostMapping("/addStaffInfo")
	public String saveStaffName(@RequestParam String staff_id, Model model) {
		int s_id = Integer.parseInt(staff_id);
		StaffInfo staffInfo = new StaffInfo(staffService.getStaffById(s_id));
		staffInfoService.addStaffInfo(staffInfo);
//		Map<String, String> doctors = getDoctors();
		model.addAttribute("doctors", doctorService.getAllDoctors());
		model.addAttribute("staff", staffInfo);
		return "choose-doctor";
	}
	
	public Map<String, String> getDoctors(){
		List<String> doctorsWithIds = employeeService.getDoctorsNamesAndIds();
		Map<String, String> doctors = new HashMap<>();
		for(int i=0; i<doctorsWithIds.size(); i++) {
			String[] doctor = doctorsWithIds.get(i).split(",");
			doctors.put(doctor[0], doctor[1]);
		}
		return doctors;
	}
	
	public Map<String, String> getNurses(){
		List<String> nursesWithIds = employeeService.getNursesNamesAndIds();
		Map<String, String> nurses = new HashMap<>();
		for(int i=0; i<nursesWithIds.size(); i++) {
			String[] nurse = nursesWithIds.get(i).split(",");
			nurses.put(nurse[0], nurse[1]);
		}
		return nurses;
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/chooseDoctor")
	public String chooseDoctor(Model model, @RequestParam int staff_info_id
			, @RequestParam String choose, @RequestParam String doctor_id) {
		
		StaffInfo staff = staffInfoService.getStaffInfoById(staff_info_id);
		int doc_id = Integer.parseInt(doctor_id);
		
		/*
		 * 
		 * validate adding more doctors
		 * 
		 * */
		if(choose.length()==0) {
			model.addAttribute("Error", "choose if you want to add more doctors or not");
			model.addAttribute("doctors", doctorService.getAllDoctors());
			model.addAttribute("staff_id", staff_info_id);
			return "choose-doctor";
		}
		if(choose.equals("yes")) {
			staffDoctorsService.addStaffDoctor(
					new StaffDoctors(staffInfoService.getStaffInfoById(staff_info_id)
							, doctorService.getDoctorById(doc_id))
					);
			model.addAttribute("doctors", doctorService.getAllDoctors());
			model.addAttribute("staff", staff);
			return "choose-doctor";
		}
		staffDoctorsService.addStaffDoctor(
				new StaffDoctors(staffInfoService.getStaffInfoById(staff_info_id)
						, doctorService.getDoctorById(doc_id))
				);
		model.addAttribute("nurses", nurseService.getAllNurses());
		model.addAttribute("staff_id", staff_info_id);
		return "choose-nurse";
	}
	
	@Secured({"ADMIN","NURSE"})
	@GetMapping("/chooseNurse")
	public String chooseNurse(@RequestParam int staff_info_id, @RequestParam String nurse_id
			, @RequestParam String choose, Model model) {
		
		int n_id = Integer.parseInt(nurse_id);
		/*
		 * 
		 * validate adding more nurses
		 * 
		 * */
		if(choose.length()==0 || choose==null ) {
			model.addAttribute("Error", "choose if you want to add more nurses or not");
			model.addAttribute("doctors", doctorService.getAllDoctors());
			model.addAttribute("staff_id", staff_info_id);
			return "choose-doctor";
		}
		if(choose.equals("yes")) {
			staffNursesService.add(
					new StaffNurses(staffInfoService.getStaffInfoById(staff_info_id)
							, nurseService.getNurseById(n_id))
					);
			model.addAttribute("nurses", nurseService.getAllNurses());
			model.addAttribute("staff_id", staff_info_id);
			return "choose-nurse";
		}
		staffNursesService.add(
				new StaffNurses(staffInfoService.getStaffInfoById(staff_info_id)
						, nurseService.getNurseById(n_id))
				);
		Map<String, String> allDoctors = getDoctors();
		Map<String, String> allNurses = getNurses();
		List<String> staffDoctorsIds = staffDoctorsService.getDoctorsIds(staff_info_id);
		List<String> staffNursesIds = staffNursesService.getNursesIds(staff_info_id);
		Map<String, String> nurses = new HashMap<>();
		Map<String, String> doctors = new HashMap<>();
		
		for(String s:allDoctors.keySet()) {
			if(staffDoctorsIds.contains(s)) {
				doctors.put(s, allDoctors.get(s));
			}
		}
		
		for(String s:allNurses.keySet()) {
			if(staffNursesIds.contains(s)) {
				nurses.put(s, allNurses.get(s));
			}
		}
		
		model.addAttribute("nurses", nurses);
		model.addAttribute("doctors", doctors);
		return "staff-operation-info";
	}
}
