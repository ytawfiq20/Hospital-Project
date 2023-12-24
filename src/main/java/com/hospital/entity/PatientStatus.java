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
@Table(name = "patient_status")
public class PatientStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String state;

    private String medicine;

    private String re_examination_date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public PatientStatus() {
    }

    public PatientStatus(String state, String medicine, String re_examination_date
        , Patient patient) {
        this.state = state;
        this.medicine = medicine;
        this.re_examination_date = re_examination_date;
        this.patient = patient;
    }

    
    public PatientStatus(int id, String state, String medicine, String re_examination_date
    		, Patient patient) {
		this.id = id;
		this.state = state;
		this.medicine = medicine;
		this.re_examination_date = re_examination_date;
		this.patient = patient;
	}

	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMedicine() {
        return this.medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getRe_examination_date() {
        return this.re_examination_date;
    }

    public void setRe_examination_date(String re_examination_date) {
        this.re_examination_date = re_examination_date;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", state='" + getState() + "'" +
            ", medicine='" + getMedicine() + "'" +
            ", re_examination_date='" + getRe_examination_date() + "'" +
            ", patient='" + getPatient() + "'" +
            "}";
    }

    
}
