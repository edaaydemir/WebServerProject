package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.EmailBody;
import com.HMSApp.HospitalMngmnt.entity.Forget;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.AppointmentRepository;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;
import com.HMSApp.HospitalMngmnt.repository.ReceiptRepository;
import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

@Service
public class PatientServiceImpl implements IPatientService {

    public static Map<String, LocalDateTime> myTimeDate = new LinkedHashMap<>();

    public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(7, new SecureRandom());

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository user;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    Appointment appointmentsave;

    @Autowired
    EmailBody emailBody;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    IEmailService emailService;

    @Autowired
    SessionRepository sessionRepository;

    public PatientServiceImpl(Appointment appointment, IEmailService emailService, EmailBody emailBody) {

        this.appointmentsave = appointment;
        this.emailService = emailService;
        this.emailBody = emailBody;

    }

    @Override
    public Patient getPatientById(Long patientid) {
        return patientRepository.findById(patientid)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientid));
    }

    @Override
    public Patient createPatient(Patient patient) throws OptionalException {
        Patient dbUser = user.findByEmail(patient.getEmail());

        if (dbUser == null) {

            patient.setType("Patient");

            patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));

            user.save(patient);

            return patient;
        }

        else {
            throw new OptionalException("Already exits a patient using this email:" + patient.getEmail());
        }

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

    @Override
    public Appointment bookAppointment(String key, Appointment appointment) throws IOException, OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bookAppointment'");
    }

    @Override
    public Appointment updateAppointment(String key, Appointment newAppointment) throws IOException, OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAppointment'");
    }

    @Override
    public Appointment deleteAppointment(Appointment appointment) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAppointment'");
    }

    @Override
    public List<Appointment> getPatientAppointment(String key) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientAppointment'");
    }

    @Override
    public List<Doctor> getAllDoctors() throws OptionalException {

        List<Doctor> listedDoc = doctorRepository.findAll();

        // Filter out invalid doctors
        List<Doctor> validDoctors = listedDoc.stream()
                .filter(doctor -> doctor.getIsValid() == true)
                .collect(Collectors.toList());

        if (!validDoctors.isEmpty()) {
            return validDoctors;
        } else {
            throw new OptionalException("There are no valid doctors currently available");
        }
    }

    @Override
    public Patient forgetPassword(String key, Forget forgetPassword) throws OptionalException {

        Session Session = sessionRepository.findByUuid(key);
        Optional<Patient> registerPatient = patientRepository.findById(Session.getUserId());
        // Check if the old password matches the current password
        boolean isMatched = bCryptPasswordEncoder.matches(forgetPassword.getOldpassword(),
                registerPatient.get().getPassword());

        if (isMatched) {
            // Update the password with the new one
            registerPatient.get().setPassword(bCryptPasswordEncoder.encode(forgetPassword.getNewpassword()));
            // Save the updated patient
            return patientRepository.save(registerPatient.get());
        } else {
            // Throw exception if the old password does not match
            throw new OptionalException("Please enter a valid old password.");
        }
    }

    @Override
    public Session getUserByUuid(String uuid) throws OptionalException {

        Session session = sessionRepository.findByUuid(uuid);

        if (session != null) {
            return session;
        }

        else {
            throw new OptionalException("ID not found");
        }

    }
}
