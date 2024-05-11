package com.HMSApp.HospitalMngmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.HospitalMngmnt.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
