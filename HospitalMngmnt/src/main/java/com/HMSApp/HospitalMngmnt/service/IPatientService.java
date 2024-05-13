package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.util.List;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Forget;
//import com.HMSApp.HospitalMngmnt.dto.PatientDto;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;

public interface IPatientService {

    Patient getPatientById(Long patientid) throws OptionalException;

    Patient createPatient(Patient patient) throws OptionalException;

    Patient updatePatient(Long patientid, Patient patientDetails) throws OptionalException;

    Appointment bookAppointment(String key, Appointment appointment) throws IOException, OptionalException; // MessagingException;

    Appointment updateAppointment(String key, Appointment newAppointment) throws OptionalException, IOException;

    Appointment deleteAppointment(Appointment appointment) throws OptionalException;

    List<Appointment> getPatientAppointment(String key) throws OptionalException;

    List<Doctor> getAllDoctors() throws OptionalException;

    Patient forgetPassword(String key, Forget forgetPassword) throws OptionalException;

    Session getUserByUuid(String uuid) throws OptionalException;

    void deletePatient(Long patientid);

}
