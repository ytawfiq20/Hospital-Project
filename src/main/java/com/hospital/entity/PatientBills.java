package com.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_bills")
public class PatientBills {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private float price;

    @Column(columnDefinition = "tinyint(1) default 0")
    private short is_paid;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

	public PatientBills() {
		super();
	}

	public PatientBills(float price, short is_paid, Patient patient) {
		this.price = price;
		this.is_paid = is_paid;
		this.patient = patient;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public short getIs_paid() {
		return is_paid;
	}

	public void setIs_paid(short is_paid) {
		this.is_paid = is_paid;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "PatientBills [id=" + id  
				+ ", price=" + price + ", is_paid=" + is_paid
				+ ", patient=" + patient + "]";
	}
    
    

}
