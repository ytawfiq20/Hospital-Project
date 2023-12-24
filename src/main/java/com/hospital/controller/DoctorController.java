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

import com.hospital.entity.Doctor;
import com.hospital.entity.Employee;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.EmployeeService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    DepartmentService departmentService;
    @Autowired 
    DoctorService doctorService;
    @Autowired
    EmployeeService employeeService;

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/addDoctor/{id}")
    public String addDoctorForm(Model model, @PathVariable int id){
        model.addAttribute("emp_id", id);
        model.addAttribute("departments", departmentService.getAllDepartmrnts());
        return "add-doctor";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/addDoctor")
    public String saveDoctor(
    		@ModelAttribute Doctor doctor, @RequestParam int id
    		, @RequestParam String department_id, Model model){
    	
    	int dep_id = Integer.parseInt(department_id);
    	Employee employeeDetails = 
    			employeeService.getEmployeeById(id);
    	
    	
        if(doctor.getWorking_days()==null){
            model.addAttribute("Error", "Enter Doctor Working days");
            model.addAttribute("examination_price", doctor.getExamination_price());
            model.addAttribute("working_hours", doctor.getWorking_hours());
            model.addAttribute("description", doctor.getDescription());
            model.addAttribute("emp_id", id);
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "add-doctor";
        }
        if(doctor.getWorking_hours().length()==0){
        	model.addAttribute("Error", "Enter Doctor Working hours");
        	model.addAttribute("examination_price", doctor.getExamination_price());
            model.addAttribute("working_hours", doctor.getWorking_hours());
            model.addAttribute("description", doctor.getDescription());
            model.addAttribute("emp_id", id);
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "add-doctor";
        }
        
        if(doctor.getExamination_price() == null) {
        	model.addAttribute("Error", "Enter Doctor Examination Price");
        	model.addAttribute("examination_price", doctor.getExamination_price());
            model.addAttribute("working_hours", doctor.getWorking_hours());
            model.addAttribute("description", doctor.getDescription());
            model.addAttribute("emp_id", id);
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "add-doctor";
        }

        Doctor doctor1 = new Doctor( id, doctor.getWorking_days(), doctor.getWorking_hours()
        		, doctor.getExamination_price(), doctor.getDescription(), employeeDetails,
    			departmentService.getDepartmentById(dep_id));
        System.out.println(doctor1);
        
        doctorService.saveDoctor(doctor1);
        employeeDetails.setIs_employed((short) 1);
        employeeDetails.setJob_title("Doctor");
		employeeService.updateEmployee(employeeDetails);
        return "redirect:/doctor/allDoctors";
    }

    @GetMapping("/allDoctors")
    public String getAllDoctors(Model model){
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "all-doctors";
    }

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/updateDoctor/{id}")
    public String updateDoctorForm(Model model, @PathVariable int id){
        model.addAttribute("doctor", doctorService.getDoctorById(id));
//        model.addAttribute("activeDays", checkActiveWorkingDays
//        		(doctorService.getDoctorById(id).getWorking_days()));
//        System.out.println(checkActiveWorkingDays
//        		(doctorService.getDoctorById(id).getWorking_days()));
        model.addAttribute("departments", departmentService.getAllDepartmrnts());
        return "update-doctor";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/updateDoctor")
    public String saveUpdatesToDoctor(
    		@RequestParam String department_id,
    		@ModelAttribute Doctor doctor, @RequestParam int id
    		, Model model){
    	
    	int dep_id = Integer.parseInt(department_id);
    	System.out.println(doctor);
    	Doctor doctor1 = new Doctor(id, doctor.getWorking_days()
    			, doctor.getWorking_hours()
    			, doctor.getExamination_price(), doctor.getDescription()
    			, employeeService.getEmployeeById(id),
    			departmentService.getDepartmentById(dep_id));
    	
        if(doctor.getWorking_days()==null){
            model.addAttribute("Error", "Enter Doctor Working days");
            model.addAttribute("doctor", doctorService.getDoctorById(id));
//            model.addAttribute("activeDays", checkActiveWorkingDays
//            		(doctorService.getDoctorById(id).getWorking_days()));
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "update-doctor";
        }
        if(doctor.getWorking_hours().length()==0){
            model.addAttribute("Error", "Enter Doctor Working hours");
            model.addAttribute("doctor", doctorService.getDoctorById(id));
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "update-doctor";
        }
        if(doctor.getExamination_price()==null) {
        	model.addAttribute("Error", "Enter Doctor Examination price");
            model.addAttribute("doctor", doctorService.getDoctorById(id));
            model.addAttribute("departments", departmentService.getAllDepartmrnts());
            return "update-doctor";
        }
        
        System.out.println(doctor1);

        doctorService.updateDoctor(doctor1);
        return "redirect:/doctor/allDoctors";
    }

//    public Map<String, Boolean> checkActiveWorkingDays(String working_days){
//    	String[] days = working_days.split(",");
//    	List<String> list = new ArrayList<>();
//    	list.addAll(Arrays.asList(days));
//    	Map<String, Boolean> map = new HashMap<>();
//    	map.put("Saturday", false);
//    	map.put("Sunday", false);
//    	map.put("Monday", false);
//    	map.put("Tuesday", false);
//    	map.put("Wednesday", false);
//    	map.put("Thursday", false);
//    	for(String s:map.keySet()) {
//    		if(list.contains(s)) {
//    			map.put(s, true);
//    		}
//    	}
//    	return map;
//    }
    
    @Secured({"ADMIN"})
    @GetMapping("/deleteDoctor/{id}")
    public String deleteDoctor(@PathVariable int id){
        doctorService.deleteDoctorById(id);
        return "redirect:/doctor/allDoctors";
    }
    
    @GetMapping("/search")
    public String search(Model model, @RequestParam String name) {
    	String name1 = removeSpaces(name);
    	model.addAttribute("doctors1", doctorService.getByName(name1));
    	model.addAttribute("doctors", doctorService.getAllDoctors());
    	return "all-doctors";
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
