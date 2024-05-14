package com.HMSApp.HospitalMngmnt.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Login;
import com.HMSApp.HospitalMngmnt.entity.LoginUserKey;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;

import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

@Service
public class DoctorLoginServiceImpl implements IDoctorLoginService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public LoginUserKey logIn(Login login) throws OptionalException {

        LoginUserKey loginUserKey = new LoginUserKey();

        Doctor doctor = doctorRepository.findByEmail(login.getEmail());

        if (doctor == null) {
            throw new OptionalException("Please enter a valid email");
        }

        Optional<Session> patientSessionOptional = sessionRepository.findById(doctor.getDoctorid());

        if (patientSessionOptional.isPresent()) {

            if (PatientServiceImpl.bCryptPasswordEncoder.matches(login.getPassword(), doctor.getPassword())) {

                loginUserKey.setUuid(patientSessionOptional.get().getUuid());
                loginUserKey.setMesasage("Logged in ");
                return loginUserKey;

            }

            throw new OptionalException("Please enter valid details");
        }

        // for postman comment from here

        // Optional<Session> validSessionOpt =
        // sessionRepository.findById(doctor.getDoctorid());

        // if (validSessionOpt.isPresent()) {

        // throw new OptionalException("Doctor already loggedin");

        // }

        // to here

        if (PatientServiceImpl.bCryptPasswordEncoder.matches(login.getPassword(), doctor.getPassword())) {

            String key = generateRandomString();

            Session currentPatientSession = new Session(doctor.getDoctorid(), key, LocalDateTime.now());

            if (PatientServiceImpl.bCryptPasswordEncoder.matches("admin", doctor.getPassword())
                    && doctor.getEmail().equals("admin@mail.com")) {

                doctor.setType("admin");
                currentPatientSession.setUserType("admin");
                currentPatientSession.setUserId(doctor.getDoctorid());

                sessionRepository.save(currentPatientSession);
                doctorRepository.save(doctor);

                loginUserKey.setMesasage("Login Successful as admin with key");

                loginUserKey.setUuid(key);

                return loginUserKey;

            } else {

                doctor.setType("doctor");
                currentPatientSession.setUserId(doctor.getDoctorid());
                currentPatientSession.setUserType("patient");

            }

            doctorRepository.save(doctor);

            sessionRepository.save(currentPatientSession);

            loginUserKey.setMesasage("Login Successful as doctor with this key");

            loginUserKey.setUuid(key);

            return loginUserKey;

        } else {

            throw new OptionalException("Please enter valid password");

        }

    }

    @Override
    public String logOut(String key) throws OptionalException {
        Session currentDoctorOptional = sessionRepository.findByUuid(key);

        if (currentDoctorOptional != null) {

            sessionRepository.delete(currentDoctorOptional);

            return "Logout successful";

        } else {

            throw new OptionalException("Please enter valid key");

        }
    }

    @Override
    public Boolean isLoggedIn(String key) throws OptionalException {
        Session currentPatientSession = sessionRepository.findByUuid(key);

        if (currentPatientSession != null) {

            return true;

        } else {

            return false;
        }
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        int length = 18; // Length of the random string
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

}
