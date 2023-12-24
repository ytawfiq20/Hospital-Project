package com.hospital.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "operation_id", nullable = false)
	private HospitalOperations hospitalOperations;
	
	@OneToMany(mappedBy = "staff")
	private Set<StaffInfo> staffs = new HashSet<>();
	
	
	public Staff() {
		
	}

	
	public Staff(int id, HospitalOperations hospitalOperations) {
		super();
		this.id = id;
		this.hospitalOperations = hospitalOperations;
	}


	public Staff(HospitalOperations hospitalOperations) {
		this.hospitalOperations = hospitalOperations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HospitalOperations getHospitalOperations() {
		return hospitalOperations;
	}

	public void setHospitalOperations(HospitalOperations hospitalOperations) {
		this.hospitalOperations = hospitalOperations;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", hospitalOperations=" + hospitalOperations + "]";
	}
	
	
	
}
