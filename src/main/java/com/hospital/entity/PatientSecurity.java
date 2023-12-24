package com.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_security")
public class PatientSecurity  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(columnDefinition = "varchar(255) default '1234'")
	private String password;
	
	@Column(columnDefinition = "tinyint(1) default 1")
	private short is_active;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
//	@OneToMany(mappedBy = "patientSecurity")
//	private Set<Role> roles = new HashSet<>();
	
	private String roles;

	public PatientSecurity() {
	}

	public PatientSecurity(int id, String username, String password
			, short is_active, Patient patient, String roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.is_active = is_active;
		this.patient = patient;
		this.roles=roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getIs_active() {
		return is_active;
	}

	public void setIs_active(short is_active) {
		this.is_active = is_active;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "PatientSecurity [id=" + id + ", username=" + username 
				+ ", password=" + password + ", is_active="
				+ is_active + ", patient=" + patient + ", roles=" + roles + "]";
	}







	
	
	

}
