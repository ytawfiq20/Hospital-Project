package com.hospital.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_info")
public class StaffInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false)
	private Staff staff;
	
	@OneToMany(mappedBy = "staffInfo")
	@Column(nullable = false)
	private Set<StaffDoctors> doctors = new HashSet<>();
	
	@OneToMany(mappedBy = "staffInfo")
	@Column(nullable = false)
	private Set<StaffNurses> nurses = new HashSet<>();
	
	@OneToMany(mappedBy = "staffInfo")
	@Column(nullable = false)
	private Set<StaffPatients> patients = new HashSet<>();

	public StaffInfo() {
	}

	
	public StaffInfo(Staff staff) {
		super();
		this.staff = staff;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Set<StaffDoctors> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<StaffDoctors> doctors) {
		this.doctors = doctors;
	}

	public Set<StaffNurses> getNurses() {
		return nurses;
	}

	public void setNurses(Set<StaffNurses> nurses) {
		this.nurses = nurses;
	}

	public Set<StaffPatients> getPatients() {
		return patients;
	}

	public void setPatients(Set<StaffPatients> patients) {
		this.patients = patients;
	}

	@Override
	public String toString() {
		return "StaffInfo [id=" + id + ", staff=" + staff + ", doctors=" 
				+ doctors + ", nurses=" + nurses + ", patients=" + patients + "]";
	}
	
	

}
