package com.HMSApp.HospitalMngmnt.entity;

import java.util.ArrayList;
import java.util.List;

import com.HMSApp.HospitalMngmnt.entity.enums.City;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorid")
    private Long doctorid;

    @Column(name = "doctorname")
    private String doctorname;

    @Column(name = "phoneNum")
    private String phoneNum;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "spec")
    private String specialization;

    @Enumerated(EnumType.STRING)
    private City city;

    private int experience;

    private String type;

    // only 24 hour format
    private Integer availableTimeFrom;
    // only 24 hour format
    private Integer availableTimeTo;

    private Boolean isValid = true;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    List<Appointment> listOfAppointments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    List<Receipt> listReceipts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    List<Message> listOfMessage = new ArrayList<>();

}
