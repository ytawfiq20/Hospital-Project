package com.hospital.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.entity.Person;
import com.hospital.entity.Role;
import com.hospital.service.PersonSecurityService;
import com.hospital.service.RoleService;
import com.hospital.validation.PersonValidation;

@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminController {
	
	@Autowired
	PersonSecurityService personSecurityService;
	@Autowired
	RoleService roleService;
	
	private PersonValidation personValidation = new PersonValidation();
	
	Set<String> getRoles(){
		String[] allRoles= {"ADMIN", "NURSE", "USER"};
		Set<String> roles = new HashSet<>();
		roles.addAll(Arrays.asList(allRoles));
		return roles;
	}


	/*
	 * 
	 * Deal with person
	 * 
	 * */
	@GetMapping("/addAuthPerson")
	public String addPersonForm() {
		return "add-auth-person";
	}
	

	@PostMapping("/addAuthPerson")
	public String savePerson(@ModelAttribute Person person, Model model) {
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
           return "add-auth-person";
       }
       if(!personValidation.validateName(person.getLast_name())) {
       	model.addAttribute("Error", "Invalid last name");
       	model.addAttribute("first_name", person.getFirst_name());
           model.addAttribute("last_name", person.getLast_name());
           model.addAttribute("username", person.getUsername());
           model.addAttribute("password", person.getPassword());
           return "add-auth-person";
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
			return "add-auth-person";
		}
		if(personSecurityService.isUserNameExists(person.getUsername())) {
			model.addAttribute("Error", "This username exists before");
			model.addAttribute("first_name", person.getFirst_name());
			model.addAttribute("last_name", person.getLast_name());
			model.addAttribute("username", person.getUsername());
			model.addAttribute("password", person.getPassword());
			return "add-auth-person";
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
			return "add-auth-person";
		}
		personSecurityService.addPerson(person);
		roleService.addRole(new Role("USER",
				personSecurityService.getByUsername(person.getUsername())));
		model.addAttribute("person", personSecurityService.getById(person.getId()));
		return "redirect:/admin/allPersons";
	}
	

	@GetMapping("/allPersons")
	public String gelAllPersons(Model model) {
		model.addAttribute("persons", personSecurityService.getAll());
		return "all-auth-persons";
	}
	
	

	@GetMapping("/updateAuthPerson/{id}")
	public String updatePerson(@PathVariable int id, Model model) {
		Person person = personSecurityService.getById(id);
		Person newPerson = new Person(id, person.getFirst_name(), person.getLast_name()
				, person.getUsername(), "", person.getRoles());
		model.addAttribute("person", newPerson);
		model.addAttribute("id", id);
		return "update-auth-person";
	}
	
	@PostMapping("/updateAuthPerson")
	public String updatePerson(@ModelAttribute Person person
			, @RequestParam String newPassword , @RequestParam int id, Model model) {
		System.out.println(person);
		
		Person oldPerson = personSecurityService.getById(id);
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	  /*
       * validate first name and last name
       */
      if(!personValidation.validateName(person.getFirst_name())){
          model.addAttribute("Error", "Invalid first name");
          model.addAttribute("person", personSecurityService.getById(id));
          model.addAttribute("id", id);
  		  return "update-auth-person";
      }
      if(!personValidation.validateName(person.getLast_name())) {
      	model.addAttribute("Error", "Invalid last name");
      	model.addAttribute("person", personSecurityService.getById(id));
      	model.addAttribute("id", id);
		return "update-auth-person";
      }
		/*
		 * validate user name
		 * */
		if(!personValidation.validateUsername(person.getUsername())) {
			model.addAttribute("Error", "Invalid username");
			model.addAttribute("person", personSecurityService.getById(id));
			model.addAttribute("id", id);
			return "update-auth-person";
		}
		if(personSecurityService.isUserNameExists(person.getUsername())
				&&!oldPerson.getUsername().equals(person.getUsername())) {
			model.addAttribute("Error", "This username exists before");
			model.addAttribute("person", personSecurityService.getById(id));
			model.addAttribute("id", id);
			return "update-auth-person";
		}
		/*
		 * validate old password
		 * */
		if(person.getPassword().length()==0) {
			model.addAttribute("Error", "Enter your old password");
			model.addAttribute("person", personSecurityService.getById(id));
			model.addAttribute("id", id);
			return "update-auth-person";
		}
		/*
		 * if old password is non encrypted
		 * */
		if(!personValidation.isPasswordEncrypted(oldPerson.getPassword())) {
			if(!oldPerson.getPassword().equals(person.getUsername())) {
				model.addAttribute("Error", "Old password is incorrect");
		        model.addAttribute("person", personSecurityService.getById(id));
		        model.addAttribute("id", id);
		  		return "update-auth-person";
			}
		}
		/*
		 * if old password is encrypted 
		 * check if old password correct
		 * */
		else {
			if(!bcrypt.matches(person.getPassword(), oldPerson.getPassword())) {
				model.addAttribute("Error", "Old password is incorrect");
		        model.addAttribute("person", personSecurityService.getById(id));
		        model.addAttribute("id", id);
		  		return "update-auth-person";
			}
		}
		/*
		 * validate new password
		 * */
		if(newPassword.length()==0 || newPassword==null) {
			model.addAttribute("Error", "Please Enter new password");
			model.addAttribute("person", personSecurityService.getById(id));
			model.addAttribute("oldPassword", oldPerson.getPassword());
			model.addAttribute("id", id);
			return "update-auth-person";
		}
		Person newPerson = new Person(id, person.getFirst_name(), person.getLast_name()
				, person.getUsername(), newPassword, person.getRoles());
		personSecurityService.update(newPerson);
		return "redirect:/admin/allPersons";
		
	}
	

	@GetMapping("/deleteAuthPerson/{id}")
	public String deletePerson(@PathVariable int id, Model model) {
		personSecurityService.deleteById(id);
		return "redirect:/admin/allPersons";
	}
	
	/*
	 * 
	 * Deal with person roles
	 * 
	 * */

	@GetMapping("/personRoles/{id}")
	public String getPersonRoles(@PathVariable int id, Model model) {
		model.addAttribute("roles", roleService.getPersonRolesByPersonId(id));
		model.addAttribute("person", personSecurityService.getById(id));
		return "person-roles";
	}
	

	@GetMapping("/addRole/{person_id}")
	public String addRoleForm(Model model, @PathVariable int person_id) {
		model.addAttribute("person_id", person_id);
		model.addAttribute("roles", getRoles());
		return "add-role";
	}
	

	@PostMapping("/addRole")
	public String saveRole(@RequestParam int person_id, @RequestParam String name) {
		roleService.addRole(new Role(name, personSecurityService.getById(person_id)));
		return "redirect:/admin/personRoles/"+person_id;
	}

	@GetMapping("/updateRole/{role_id}")
	public String updateRoleForm(Model model, @PathVariable int role_id) {
		model.addAttribute("role_id", role_id);
		model.addAttribute("roles", getRoles());
		return "update-role";
	}

	@PostMapping("/updateRole")
	public String saveUpdateToRole(Model model, @RequestParam int id
			, @RequestParam String name) {
		Person person = roleService.getById(id).getPerson();
		roleService.updateRole(new Role(id, name, person));
		return "redirect:/admin/personRoles/"+person.getId();
	}
	

	@GetMapping("/deleteRole/{role_id}")
	public String deleteRole(@PathVariable int role_id) {
		Person person = roleService.getById(role_id).getPerson();
		person.getRoles().removeIf(r->r.getId()==role_id);
		roleService.deleteById(role_id);
		return "redirect:/admin/personRoles/"+person.getId();
	}
	
	/*
	 * search
	 * */
	@GetMapping("/search")
	public String search(@RequestParam String name, Model model) {
		String name1 = removeSpaces(name);
		model.addAttribute("persons1", personSecurityService.getByName(name1));
		model.addAttribute("persons", personSecurityService.getAll());
		return "all-auth-persons";
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
