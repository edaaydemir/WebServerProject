package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;

public interface IDoctorService {
    List<Doctor> getAllDoctors() throws OptionalException;

    Doctor getDoctorById(Long doctorid) throws OptionalException;

    List<LocalDateTime> getAvailableDoctorForBooking(String key, Doctor doctor) throws IOException, OptionalException;

    Doctor getDoctorDetails(String key) throws OptionalException;

    List<Appointment> getPastAppointment(Doctor doctor) throws OptionalException;

    List<Appointment> getFutureAppointment(Doctor doctor) throws OptionalException;

    List<Appointment> getAllAppointments(Doctor registerDoctor) throws OptionalException;

    List<Patient> getListOfPatient();

    Receipt getReceiptOfDoctorByPatient(String key, Receipt Receipt) throws OptionalException;

    Receipt deleteReceipt(String key, Receipt Receipt) throws OptionalException;

}
