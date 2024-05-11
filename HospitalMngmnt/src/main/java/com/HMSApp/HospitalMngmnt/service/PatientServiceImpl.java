package com.HMSApp.HospitalMngmnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long patientid) {
        return patientRepository.findById(patientid)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientid));
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long patientid, Patient patientDetails) {
        Patient patient = getPatientById(patientid);
        patient.setPatientname(patientDetails.getPatientname());
        patient.setPatientsurname(patientDetails.getPatientsurname());
        patient.setAge(patientDetails.getAge());
        patient.setPhoneNum(patientDetails.getPhoneNum());
        patient.setGender(patientDetails.getGender());
        patient.setDateOfBorn(patientDetails.getDateOfBorn());
        patient.setEmail(patientDetails.getEmail());
        patient.setPassword(patientDetails.getPassword());
        patient.setCity(patientDetails.getCity());
        patient.setStatus(patientDetails.getStatus());
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long patientid) {
        patientRepository.deleteById(patientid);
    }
}
