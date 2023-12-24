package com.hospital.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Department;
import com.hospital.repository.DepartmentRepo;

@Service
public class DepartmentService {
    
    private DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo){
        this.departmentRepo=departmentRepo;
    }

    public void saveDepartment(Department department){
        departmentRepo.save(department);
    }

    public void deleteDepartmentById(int id){
        departmentRepo.deleteById(id);
    }

    public Department getDepartmentById(int id){
        Optional<Department> optional = departmentRepo.findById(id);
        Department department = null;
        if(optional.isPresent()){
            department = optional.get();
        }
        return department;
    }

    public List<Department> getAllDepartmrnts(){
        return departmentRepo.findAll();
    }

    public void updateDepartment(Department department){
        Department oldDepartment = getDepartmentById(department.getId());
        oldDepartment.setId(department.getId());
        oldDepartment.setName(department.getName());
        departmentRepo.save(oldDepartment);
    }
    
    public String getDepartmentNameById(int id) {
    	return departmentRepo.getDepartmentNameById(id);
    }

}
