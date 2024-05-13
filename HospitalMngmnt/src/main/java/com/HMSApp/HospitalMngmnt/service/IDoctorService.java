package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;

public interface IDoctorService {
    List<Doctor> getAllDoctors() throws OptionalException;

    Doctor getDoctorById(Integer doctorid) throws OptionalException;

    Doctor getDoctorDetails(String key) throws OptionalException;

    List<LocalDateTime> getAvailableDoctorForBooking(String key, Doctor doctor) throws IOException, OptionalException;

    List<Appointment> getPastAppointment(Doctor doctor) throws OptionalException;

    List<Appointment> getFutureAppointment(Doctor doctor) throws OptionalException;

    List<Appointment> getAllAppointments(Doctor registerDoctor) throws OptionalException;

    List<Patient> getListOfPatient();

    Receipt getReceiptOfDoctorByPatient(String key, Receipt Receipt) throws OptionalException;

    Receipt deleteReceipt(String key, Receipt Receipt) throws OptionalException;

    Receipt makeReviewToDoctorAppointment(String key, Receipt review)
            throws OptionalException;

    Receipt updateReview(String key, Receipt review) throws OptionalException;

    Session getCurrentUserByUuid(String uuid) throws OptionalException;

}
