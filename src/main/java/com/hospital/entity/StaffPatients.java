package com.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_patients")
public class StaffPatients {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "staff_info_id", nullable = false)
	private StaffInfo staffInfo;
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	public StaffPatients() {
	}



	public StaffPatients(StaffInfo staffInfo, Patient patient) {
		super();
		this.staffInfo = staffInfo;
		this.patient = patient;
	}



	public StaffPatients(int id, StaffInfo staffInfo, Patient patient) {
		super();
		this.id = id;
		this.staffInfo = staffInfo;
		this.patient = patient;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}



	public StaffInfo getStaffInfo() {
		return staffInfo;
	}



	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}



	@Override
	public String toString() {
		return "StaffPatients [id=" + id + ", staffInfo=" + staffInfo 
				+ ", patient=" + patient + "]";
	}


	

}
