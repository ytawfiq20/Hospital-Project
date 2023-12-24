package com.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hospital.entity.Person;
import com.hospital.entity.Role;
import com.hospital.repository.PersonRepo;
import com.hospital.service.PersonSecurityService;
import com.hospital.service.RoleService;


@SpringBootApplication
public class Hospital1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hospital1Application.class, args);
	}
	
	@Autowired
	RoleService roleService;
	@Autowired
	PersonSecurityService personSecurityService;

//    @Bean
//    public CommandLineRunner runner(PersonRepo p, PasswordEncoder encoder)
//    	throws Exception{
//    	
//    	return args->{
//    		p.save(new Person("Yousef", "Mohamed", "admin", encoder.encode("admin")));
//    		p.save(new Person("Walid", "Ahmed", "user1", encoder.encode("user1")));
//    		roleService.addRole(new Role("ADMIN", personSecurityService
//    				.getById(1)));
//    		roleService.addRole(new Role("USER", personSecurityService
//    				.getById(1)));
//    		roleService.addRole(new Role("USER", personSecurityService
//    				.getById(2)));
//    		
//    	};
//    }

}


