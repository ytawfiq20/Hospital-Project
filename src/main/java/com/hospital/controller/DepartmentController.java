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

import com.hospital.entity.Department;
import com.hospital.service.DepartmentService;
import com.hospital.validation.DepartmentValidation;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    
    @Autowired
    DepartmentService departmentService;

    DepartmentValidation departmentValidation = new DepartmentValidation();

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/addDepartment")
    public String addDepartmentForm(){
        return "add-department";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/addDepartment")
    public String saveDepartment(@ModelAttribute Department department, Model model){

        if(!departmentValidation.validateName(department.getName())){
            model.addAttribute("Error", "Invalid name");
            return "add-department"; 
        }

        departmentService.saveDepartment(department);
        return "redirect:/department/allDepartments";

    }

    @GetMapping("/allDepartments")
    public String allDepartments(Model model){
        model.addAttribute("departments", departmentService.getAllDepartmrnts());
        return "all-departments";
    }

    @Secured({"ADMIN","NURSE"})
    @GetMapping("/updateDepartment/{id}")
    public String updateDepartmentForm(@PathVariable int id, Model model){
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "update-department";
    }

    @Secured({"ADMIN","NURSE"})
    @PostMapping("/updateDepartment")
    public String saveUpdatesToDepartment(@ModelAttribute Department department, Model model){
        if(!departmentValidation.validateName(department.getName())){
            model.addAttribute("Error", "Invalid name");
            model.addAttribute("department", 
                departmentService.getDepartmentById(department.getId()));
            return "update-department";
        }
        departmentService.updateDepartment(department);
        return "redirect:/department/allDepartments";
    }

    @Secured({"ADMIN"})
    @GetMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable int id){
        departmentService.deleteDepartmentById(id);
        return "redirect:/department/allDepartments";
    }

}
