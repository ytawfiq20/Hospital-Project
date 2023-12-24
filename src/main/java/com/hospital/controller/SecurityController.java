package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.entity.Person;
import com.hospital.entity.Role;
import com.hospital.service.PersonSecurityService;
import com.hospital.service.RoleService;
import com.hospital.validation.PersonValidation;

@Controller
@RequestMapping("/security")
public class SecurityController {
	
	@Autowired
	PersonSecurityService personSecurityService;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder encoder;
	
	private PersonValidation personValidation = new PersonValidation();

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	
	@PostMapping("/signup")
	public String saveUser(@ModelAttribute Person person, Model model) {
		System.out.println(person);
		 /*
         * validate first name and last name
         */
        if(!personValidation.validateName(person.getFirst_name())){
            model.addAttribute("Error", "Invalid first name");
            model.addAttribute("first_name", person.getFirst_name());
            model.addAttribute("last_name", person.getLast_name());
            model.addAttribute("username", person.getUsername());
            model.addAttribute("password", person.getPassword());
            return "signup";
        }
        if(!personValidation.validateName(person.getLast_name())) {
        	model.addAttribute("Error", "Invalid last name");
        	model.addAttribute("first_name", person.getFirst_name());
            model.addAttribute("last_name", person.getLast_name());
            model.addAttribute("username", person.getUsername());
            model.addAttribute("password", person.getPassword());
            return "signup";
        }
		/*
		 * validate user name
		 * */
		if(!personValidation.validateUsername(person.getUsername())) {
			model.addAttribute("Error", "Invalid username");
			model.addAttribute("first_name", person.getFirst_name());
			model.addAttribute("last_name", person.getLast_name());
			model.addAttribute("username", person.getUsername());
			model.addAttribute("password", person.getPassword());
			return "signup";
		}
		if(personSecurityService.isUserNameExists(person.getUsername())) {
			model.addAttribute("Error", "This username exists before");
			model.addAttribute("first_name", person.getFirst_name());
			model.addAttribute("last_name", person.getLast_name());
			model.addAttribute("username", person.getUsername());
			model.addAttribute("password", person.getPassword());
			return "signup";
		}
		/*
		 * validate password
		 * */
		if(person.getPassword().length()==0) {
			model.addAttribute("Error", "Enter your password");
			model.addAttribute("first_name", person.getFirst_name());
			model.addAttribute("last_name", person.getLast_name());
			model.addAttribute("username", person.getUsername());
			model.addAttribute("password", person.getPassword());
			return "signup";
		}
		personSecurityService.addPerson(person);
		roleService.addRole(new Role("USER",
				personSecurityService.getByUsername(person.getUsername())));
//		roleService.addRole(new Role("ADMIN",
//				personSecurityService.getByUsername(person.getUsername())));
//		roleService.addRole(new Role("NURSE",
//				personSecurityService.getByUsername(person.getUsername())));
		return "home-page";
		
	}
	
//	@GetMapping("/signin")
//	public String signin() {
//		return "signin";
//	}
	
//	@PostMapping("/signin")
//	public String signin(@RequestParam String username, @RequestParam String password
//			, Model model) {
//		/*
//		 * check if user enter user name and password
//		 * */
//		if(username.isBlank()) {
//			model.addAttribute("Error", "Enter your username");
//			return "signin";
//		}
//		if(password.isBlank()) {
//			model.addAttribute("username", username);
//			model.addAttribute("Error", "Enter passsword");
//			return "signin";
//		}
//		/*
//		 * check if user name exists
//		 * */
//		if(personSecurityService.isUserNameExists(username)) {
//			Person person = personSecurityService.getByUsername(username);
//			if(encoder.matches(password, person.getPassword())) {
//				model.addAttribute("username", username);
//				model.addAttribute("password", password);
//				return "redirect:/login";
//			}
//			else {
//				model.addAttribute("Error", "Password is incorrect");
//				return "signin";
//			}
//		}
//		else {
//			model.addAttribute("Error", "Password is incorrect");
//			return "signin";
//		}
//	}
	
	
}
