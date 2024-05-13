package com.HMSApp.HospitalMngmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.HospitalMngmnt.entity.Patient;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Patient findByEmail(String email);

    public Patient findByPatientid(Long patientid);

}
