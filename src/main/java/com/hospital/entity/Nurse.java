package com.hospital.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
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
@Table(name = "nurse")
public class Nurse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String working_days;
	
	@Column(nullable = false)
	private String working_hours;
	
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private String created_at;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private String updated_at;
    
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    public Nurse() {
    	
    }

	public Nurse(int id, String working_days, String working_hours, Employee employee) {
		super();
		this.id = id;
		this.working_days = working_days;
		this.working_hours = working_hours;
		this.employee = employee;
	}
	
	public Nurse(String working_days, String working_hours, Employee employee) {
		this.working_days = working_days;
		this.working_hours = working_hours;
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorking_days() {
		return working_days;
	}

	public void setWorking_days(String working_days) {
		this.working_days = working_days;
	}

	public String getWorking_hours() {
		return working_hours;
	}

	public void setWorking_hours(String working_hours) {
		this.working_hours = working_hours;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Nurse [id=" + id + ", working_days=" + working_days + ", working_hours=" 
				+working_hours + ", created_at=" + created_at + ", updated_at=" + updated_at 
				+ ", employee=" + employee + "]";
	}
	
	
    
    
    

}
