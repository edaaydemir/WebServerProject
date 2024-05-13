package com.HMSApp.HospitalMngmnt.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.HMSApp.HospitalMngmnt.entity.Login;
import com.HMSApp.HospitalMngmnt.entity.LoginUserKey;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;
import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

public class PatientOrAdminLoginServiceImpl implements IPatientOrAdminLoginService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    public LoginUserKey login(Login login) throws OptionalException {

        LoginUserKey loginUserKey = new LoginUserKey();

        Patient patient = patientRepository.findByEmail(login.getEmail());

        if (patient == null) {
            throw new OptionalException("Geçerli email gir");
        }

        Optional<Session> patientSessionOptional = sessionRepository.findById(patient.getPatientid());

        if (patientSessionOptional.isPresent()) {

            if (PatientServiceImpl.bCryptPasswordEncoder.matches(login.getPassword(), patient.getPassword())) {

                loginUserKey.setUuid(patientSessionOptional.get().getUuid());
                loginUserKey.setMesasage("Logged in ");
                return loginUserKey;

            }

            throw new OptionalException("Please enter valid details");
        }

        if (patientSessionOptional.isPresent()) {

            throw new OptionalException("User already login");

        }

        if (PatientServiceImpl.bCryptPasswordEncoder.matches(login.getPassword(), patient.getPassword())) {

            String key = generateRandomString();

            Session currentPatientSession = new Session(patient.getPatientid(), key, LocalDateTime.now());

            if (PatientServiceImpl.bCryptPasswordEncoder.matches("admin", patient.getPassword())
                    && patient.getEmail().equals("admin")) {

                patient.setType("admin");
                currentPatientSession.setUserType("admin");
                currentPatientSession.setUserId(patient.getPatientid());

                sessionRepository.save(currentPatientSession);
                patientRepository.save(patient);

                loginUserKey.setMesasage("Login Successful as admin with key");

                loginUserKey.setUuid(key);

                return loginUserKey;

            } else {

                patient.setType("patient");
                currentPatientSession.setUserId(patient.getPatientid());
                currentPatientSession.setUserType("patient");

            }

            patientRepository.save(patient);

            sessionRepository.save(currentPatientSession);

            loginUserKey.setMesasage("Login Successful as patient with this key");

            loginUserKey.setUuid(key);

            return loginUserKey;

        } else {

            throw new OptionalException("Please enter valid password");

        }

    }

    @Override
    public String logout(String key) throws OptionalException {
        Session session = sessionRepository.findByUuid(key);

        if (session == null) {
            throw new OptionalException("Key geçerli değil");
        } else {

            sessionRepository.delete(session);

            return "Çıkış yapıldı";

        }
    }

    @Override
    public Boolean isLogged(String key) throws OptionalException {
        Session session = sessionRepository.findByUuid(key);

        if (session != null) {

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
