package com.HMSApp.HospitalMngmnt.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.HMSApp.HospitalMngmnt.entity.enums.City;
import com.HMSApp.HospitalMngmnt.entity.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patientid")
	private long patient_id;

	@Column(name = "patient_name")
	private String patient_name;

	@Column(name = "patient_surname")
	private String patient_surname;

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

	@Enumerated(EnumType.STRING)
	private City city;

	@Enumerated(EnumType.STRING)
	private Status status;

}
