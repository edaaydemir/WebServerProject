package com.HMSApp.HospitalMngmnt.service;

import java.util.List;

//import com.HMSApp.HospitalMngmnt.dto.PatientDto;
import com.HMSApp.HospitalMngmnt.entity.Patient;

public interface IPatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(Long patientid);

    Patient createPatient(Patient patient);

    Patient updatePatient(Long patientid, Patient patientDetails);

    void deletePatient(Long patientid);

}
