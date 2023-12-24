package com.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_nurses")
public class StaffNurses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "staff_info_id", nullable = false)
	private StaffInfo staffInfo;
	
	@ManyToOne
	@JoinColumn(name = "nurse_id", nullable = false)
	private Nurse nurse;

	public StaffNurses() {
	}

	public StaffNurses(int id, StaffInfo staffInfo, Nurse nurse) {
		super();
		this.id = id;
		this.staffInfo = staffInfo;
		this.nurse = nurse;
	}
	

	public StaffNurses(StaffInfo staffInfo, Nurse nurse) {
		super();
		this.staffInfo = staffInfo;
		this.nurse = nurse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}



	public StaffInfo getStaffInfo() {
		return staffInfo;
	}



	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}



	@Override
	public String toString() {
		return "StaffNurses [id=" + id + ", staffInfo=" + staffInfo + ", nurse=" + nurse + "]";
	}






	

}
