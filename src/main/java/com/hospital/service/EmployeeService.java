package com.hospital.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Employee;
import com.hospital.repository.EmployeeRepo;

@Service
public class EmployeeService {
    
    private EmployeeRepo employeeRepo;

    public EmployeeService (EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    public void saveEmployee(Employee employee){
        employeeRepo.save(employee);
    }

    public void deleteEmployeeById(int id){
        employeeRepo.deleteById(id);
    }

    public Employee getEmployeeById(int id){
        Optional<Employee> optional = employeeRepo.findById(id);
        Employee employee = null;
        if(optional.isPresent()){
            employee = optional.get();
        }
        return employee;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    public boolean isEmailExists(String email){
        return employeeRepo.isEmailExists(email)==1;
    }

    public boolean isPhoneNumberExists(String phone_number){
        return employeeRepo.isPhoneNumberExists(phone_number)==1;
    }

    public void updateEmployee(Employee employee){
        Employee oldEmployee = getEmployeeById(employee.getId());
        oldEmployee.setFirst_name(employee.getFirst_name());
        oldEmployee.setLast_name(employee.getLast_name());
        oldEmployee.setCreated_at(employee.getCreated_at());
        oldEmployee.setEmail(employee.getEmail());
        oldEmployee.setPhone_number(employee.getPhone_number());
        oldEmployee.setUpdated_at(employee.getUpdated_at());
        oldEmployee.setIs_employed(employee.getIs_employed());
        oldEmployee.setJob_title(employee.getJob_title());
        employeeRepo.save(oldEmployee);
    }
    
    public List<String> getDoctorsNamesById(int id){
    	return employeeRepo.getDoctorsNames(id);
    }
    
    public String getDoctorNameById(int id) {
    	return employeeRepo.getDoctorNameById(id);
    }

    public List<String> getDoctorsNamesAndIds(){
    	return employeeRepo.getDoctorsNamesAndIds();
    }
    
    public List<String> getNursesNamesAndIds(){
    	return employeeRepo.getNursesNamesAndIds();
    }
    public List<Employee> getByName(String name){
    	return employeeRepo.getByName(name);
    }
    
    public List<Employee> getByNameLike(String name){
    	return employeeRepo.getByNameLike(name);
    }
}
