package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;

public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Doctor> getAllDoctors() throws OptionalException {
        List<Doctor> docs = doctorRepository.findAll();
        if (!docs.isEmpty()) {
            return docs;
        }

        else {
            throw new OptionalException("No Doctors regisered");
        }

    }

    @Override
    public Doctor getDoctorById(Long doctorid) throws OptionalException {

        return doctorRepository.findById(doctorid)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + doctorid));
    }

    @Override
    public List<LocalDateTime> getAvailableDoctorForBooking(String key, Doctor doctor)
            throws IOException, OptionalException {

        List<LocalDateTime> avaialbelDoc = new ArrayList<>();

        Optional<Doctor> registeredDoctor = doctorRepository.findById(doctor.getDoctorid());

        if (registeredDoctor.isPresent()) {

        }

        throw new UnsupportedOperationException("Unimplemented method 'getAvailableDoctorForBooking'");
    }

    @Override
    public List<Appointment> getFutureAppointment(Doctor doctor) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFutureAppointment'");
    }

    @Override
    public List<Appointment> getPastAppointment(Doctor doctor) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPastAppointment'");
    }

    @Override
    public List<Appointment> getAllAppointments(Doctor registerDoctor) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAppointments'");
    }

    @Override
    public Doctor getDoctorDetails(String key) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDoctorDetails'");
    }

    @Override
    public List<Patient> getListOfPatient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListOfPatient'");
    }

    @Override
    public Receipt getReceiptOfDoctorByPatient(String key, Receipt Receipt) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReceiptOfDoctorByPatient'");
    }

    @Override
    public Receipt deleteReceipt(String key, Receipt Receipt) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteReceipt'");
    }

}
