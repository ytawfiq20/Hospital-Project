package com.hospital.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String first_name;
	
	@Column(nullable = false)
	private String last_name;
	
	@Column(nullable = false)
	private String phone_number;
	
	private String email;
	
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private String created_at;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private String updated_at;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<PatientDepartments> departments = new HashSet<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<PatientExaminationDate> patientDates = new HashSet<>();

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<PatientBills> patientBills = new HashSet<>();

	@OneToMany(mappedBy="patient", cascade = CascadeType.ALL)
	private Set<PatientStatus> patientConditions = new HashSet<>();
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	private List<ExaminationSession> examinationSessions = new ArrayList<>();
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private PatientSecurity patientSecurity;
    
	
	public Patient() {
		
	}

	public Patient(String first_name, String last_name, String phone_number, String email) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
	

}
