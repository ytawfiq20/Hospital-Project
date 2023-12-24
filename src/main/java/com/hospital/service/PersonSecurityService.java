package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.entity.Person;
import com.hospital.repository.PersonRepo;
import com.hospital.security.PersonSecurityDetails;

@Service
public class PersonSecurityService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	private PersonRepo personRepo;
	
	public PersonSecurityService(PersonRepo personRepo) {
		this.personRepo = personRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Optional<Person> optional = personRepo.findByUsername(username);
		if(optional.isPresent()) {
			return new PersonSecurityDetails(optional.get());
		}
		return (UserDetails)
				new UsernameNotFoundException("No data found for username: "+username);
	}
	
	public void addPerson(Person person) {
		personRepo.save(new Person(person.getFirst_name(), person.getLast_name()
				, person.getUsername(), passwordEncoder.encode(person.getPassword())));
	}
	
	public void deleteById(int id) {
		personRepo.deleteById(id);
	}
	
	public List<Person> getAll(){
		return personRepo.findAll();
	}
	
	public Person getById(int id){
		Optional<Person> optional = personRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void update(Person person) {
		Person old = getById(person.getId());
		old.setFirst_name(person.getFirst_name());
		old.setLast_name(person.getLast_name());
		old.setPassword(passwordEncoder.encode(person.getPassword()));
		old.setUsername(person.getUsername());
		personRepo.save(old);
	}
	
	public boolean isUserNameExists(String username) {
		return personRepo.isUsernameExists(username)==1;
	}
	
	public Person getByUsername(String username) {
		Optional<Person> optional = personRepo.findByUsername(username);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public List<Person> getByName(String name) {
		return personRepo.getByName(name);
	}
	
	public List<Person> getByNameLike(String name) {
		return personRepo.getByNameLike(name);
	}
	


}
