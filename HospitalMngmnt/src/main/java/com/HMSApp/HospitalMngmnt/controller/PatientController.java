package com.HMSApp.HospitalMngmnt.controller;

import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.service.IPatientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientid}")
    public Patient getPatientById(@PathVariable Long patientid) {
        return patientService.getPatientById(patientid);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{patientid}")
    public Patient updatePatient(@PathVariable Long patientid, @RequestBody Patient patientDetails) {
        return patientService.updatePatient(patientid, patientDetails);
    }

    @DeleteMapping("/{patientid}")
    public void deletePatient(@PathVariable Long patinetid) {
        patientService.deletePatient(patinetid);
    }

}
