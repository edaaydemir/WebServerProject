package com.HMSApp.HospitalMngmnt.dto;

import java.util.Date;

import com.HMSApp.HospitalMngmnt.entity.enums.City;
import com.HMSApp.HospitalMngmnt.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PatientDto {
    private Long patient_id;
    private String patient_name;
    private String patient_surname;
    private int age;
    private String phoneNum;
    private String gender;
    private Date dateOfBorn;
    private String email;
    private City city;
    private Status status;
    private String password;

}
