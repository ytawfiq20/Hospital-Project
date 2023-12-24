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
import com.hospital.service.EmployeeService;
import com.hospital.validation.EmployeeValidation;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    EmployeeValidation employeeValidation = new EmployeeValidation();

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/addEmployee")
    public String addEmployeeForm(){
        return "add-employee";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/addEmployee")
    public String saveEmployee(@ModelAttribute Employee employee, Model model){
        /*
         * validate first name and last name
         */
        if(!employeeValidation.validateName(employee.getFirst_name())){
            model.addAttribute("Error", "Invalid first name");
            model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }
        if(!employeeValidation.validateName(employee.getLast_name())) {
        	model.addAttribute("Error", "Invalid first name");
        	model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }
        /*
         * validate email
         */
        if(!employeeValidation.validateEmail(employee.getEmail())){
            model.addAttribute("Error", "Invalid email");
            model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }
        else if(employeeService.isEmailExists(employee.getEmail())){
            model.addAttribute("Error", "Email exists before");
            model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }
        /*
         * validate phone number
         */
        if(!employeeValidation.validatePhoneNumber(employee.getPhone_number())){
            model.addAttribute("Error", "Invalid phone number");
            model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }
        else if(employeeService.isPhoneNumberExists(employee.getPhone_number())){
            model.addAttribute("Error", "phone number exists before");
            model.addAttribute("first_name", employee.getFirst_name());
            model.addAttribute("last_name", employee.getLast_name());
            model.addAttribute("email", employee.getEmail());
            model.addAttribute("phone_number", employee.getPhone_number());
            return "add-employee";
        }

        employeeService.saveEmployee(employee);
        return "redirect:/employee/allEmployees";
        
    }

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/allEmployees")
    public String getAllEmployees(Model model){
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "all-employees";
    }

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployeeForm(@PathVariable int id, Model model){
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "update-employee";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/updateEmployee")
    public String saveUpdatesToEmployee(@ModelAttribute Employee employee, Model model){

        System.out.println(employee);
        
        Employee oldEmployee = employeeService.getEmployeeById(employee.getId());
        System.out.println(oldEmployee);
        /*
         * validate first name and last name
         */
        if(!employeeValidation.validateName(employee.getFirst_name())){
            model.addAttribute("Error", "Invalid first name");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }
        
        if(!employeeValidation.validateName(employee.getLast_name())){
        	model.addAttribute("Error", "Invalid last name");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }

        /*
         * validate email
         */
        if(!employeeValidation.validateEmail(employee.getEmail())){
            model.addAttribute("Error", "Invalid email");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }
        if(employeeService.isEmailExists(employee.getEmail())
            &&!employee.getEmail().equals(oldEmployee.getEmail())){
            model.addAttribute("Error", "Email exists before");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }
        /*
         * validate phone number
         */
        if(!employeeValidation.validatePhoneNumber(employee.getPhone_number())){
            model.addAttribute("Error", "Invalid phone number");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }
        if(employeeService.isPhoneNumberExists(employee.getPhone_number())
        &&!employee.getPhone_number().equals(oldEmployee.getPhone_number())){
            model.addAttribute("Error", "phone number exists before");
            model.addAttribute("employee", employeeService
                .getEmployeeById(employee.getId()));
            return "update-employee";
        }
        
        employee.setIs_employed(oldEmployee.getIs_employed());
        employee.setJob_title(oldEmployee.getJob_title());
        employeeService.updateEmployee(employee);
        return "redirect:/employee/allEmployees";
    }

    @Secured({"ADMIN"})
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee/allEmployees";
    }

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/chooseJob/{id}")
    public String chooseJob(@PathVariable int id, Model model){
        model.addAttribute("emp_id", id);
        return "choose-job";
    }
    /*
     * search
     * */
    @Secured({"ADMIN","NURSE"})
    @GetMapping("/search")
    public String search(Model model, @RequestParam String name) {
    	String name1 = removeSpaces(name);
    	model.addAttribute("employees1", employeeService.getByName(name1));
    	model.addAttribute("employees", employeeService.getAllEmployees());
    	return "all-employees";
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
