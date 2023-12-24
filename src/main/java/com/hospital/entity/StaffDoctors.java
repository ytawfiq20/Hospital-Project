package com.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_doctors")
public class StaffDoctors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "staff_info_id", nullable = false)
	private StaffInfo staffInfo;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	public StaffDoctors() {
	}



	public StaffDoctors(int id, StaffInfo staffInfo, Doctor doctor) {
		this.id = id;
		this.staffInfo = staffInfo;
		this.doctor = doctor;
	}



	public StaffDoctors(StaffInfo staffInfo, Doctor doctor) {
		this.staffInfo = staffInfo;
		this.doctor = doctor;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}



	public StaffInfo getStaffInfo() {
		return staffInfo;
	}



	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}



	@Override
	public String toString() {
		return "StaffDoctors [id=" + id + ", staffInfo=" + staffInfo 
				+ ", doctor=" + doctor + "]";
	}


	
	

}
