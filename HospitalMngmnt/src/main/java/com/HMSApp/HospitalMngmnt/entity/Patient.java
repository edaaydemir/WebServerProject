package com.HMSApp.HospitalMngmnt.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.HMSApp.HospitalMngmnt.entity.enums.City;
import com.HMSApp.HospitalMngmnt.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patientid")
	private Integer patientid;

	@Column(name = "patientname")
	private String patientname;

	@Column(name = "patientsurname")
	private String patientsurname;

	@Column(name = "age")
	private int age;

	@Column(name = "phoneNum")
	private String phoneNum;

	@Column(name = "gender")
	private String gender;

	@Column(name = "bornDate")
	private Date dateOfBorn;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Enumerated(EnumType.STRING)
	private City city;

	@Enumerated(EnumType.STRING)
	private Status status;

	// doctor or patient information
	private String type;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Appointment> listOfAppointments = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<Receipt> listReceipts = new ArrayList<>();

}
