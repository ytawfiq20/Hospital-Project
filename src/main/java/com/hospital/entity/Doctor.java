package com.hospital.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String working_days;

    @Column(nullable = false)
    private String working_hours;

    @Column(nullable = false)
    private Integer examination_price;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private String created_at;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private String updated_at;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<ExaminationSession> examinationSessions = new ArrayList<>();

    public Doctor() {
    }



    public Doctor(String working_days, String working_hours,
    		int examination_price, String description,
			Employee employee, Department department) {
		this.working_days = working_days;
		this.working_hours = working_hours;
		this.examination_price = examination_price;
		this.description = description;
		this.employee = employee;
		this.department = department;
	}
    
    public Doctor(int id, String working_days, String working_hours,
    		Integer examination_price, String description,
			Employee employee, Department department) {
    	this.id = id;
		this.working_days = working_days;
		this.working_hours = working_hours;
		this.examination_price = examination_price;
		this.description = description;
		this.employee = employee;
		this.department = department;
	}



	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorking_days() {
        return this.working_days;
    }

    public void setWorking_days(String working_days) {
        this.working_days = working_days;
    }

    public String getWorking_hours() {
        return this.working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public Integer getExamination_price() {
        return this.examination_price;
    }

    public void setExamination_price(Integer examination_price) {
        this.examination_price = examination_price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", working_days='" + getWorking_days() + "'" +
            ", working_hours='" + getWorking_hours() + "'" +
            ", examination_price='" + getExamination_price() + "'" +
            ", description='" + getDescription() + "'" +
            ", employee='" + getEmployee() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }

    
}
