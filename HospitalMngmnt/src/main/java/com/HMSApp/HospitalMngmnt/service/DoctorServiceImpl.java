package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;
import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public List<Doctor> getAllDoctors() throws OptionalException {
        List<Doctor> allDocs = doctorRepository.findAll();

        if (!allDocs.isEmpty()) {
            return allDocs;
        }

        else {
            throw new OptionalException("Doctor yok");
        }

    }

    @Override
    public Doctor getDoctorById(Integer doctorid) throws OptionalException {
        return doctorRepository.findById(doctorid)
                .orElseThrow(() -> new RuntimeException("Bu idli doktor yok: " + doctorid));
    }

    @Override
    public Doctor getDoctorDetails(String key) throws OptionalException {
        Session currentDoctor = sessionRepository.findByUuid(key);

        Optional<Doctor> doctor = doctorRepository.findById(currentDoctor.getUserId());

        if (doctor.isPresent()) {

            return doctor.get();

        } else {

            throw new OptionalException("Doctor not present by this uuid " + key);
        }
    }

    @Override
    public List<LocalDateTime> getAvailableDoctorForBooking(String key, Doctor doctor)
            throws OptionalException, IOException {
        Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorid());

        List<LocalDateTime> doctorAvailableTiming = new ArrayList<>();

        if (registerDoctor.isPresent()) {

            PatientServiceImpl.getAppointmentDates(registerDoctor.get().getAvailableTimeFrom(),
                    registerDoctor.get().getAvailableTimeTo());

            Map<String, LocalDateTime> dateTimeMap = PatientServiceImpl.dateTimeMap;

            List<Appointment> listedDoc = registerDoctor.get().getListOfAppointments();

            for (String string : dateTimeMap.keySet()) {

                Boolean bool = false;

                for (Appointment eachAppointment : listedDoc) {

                    LocalDateTime localDateTime = dateTimeMap.get(string);

                    if (localDateTime.isEqual(eachAppointment.getAppointmentDateAndTime())) {

                        bool = true;
                        break;

                    }
                }

                if (bool == false) {

                    doctorAvailableTiming.add(dateTimeMap.get(string));

                }
            }

            if (!doctorAvailableTiming.isEmpty()) {

                return doctorAvailableTiming;

            } else {

                throw new OptionalException("MEvcut randevu yok");
            }

        } else {

            throw new OptionalException("Böyle bir doktoryok: " + doctor.getDoctorid());
        }
    }

    @Override
    public List<Appointment> getPastAppointment(Doctor doctor) throws OptionalException {
        List<Appointment> listOfAppointments = doctor.getListOfAppointments();

        List<Appointment> listOfPastAppointments = new ArrayList<>();

        LocalDateTime currentTimeAndDate = LocalDateTime.now();

        testing();

        try {

            for (Appointment eachAppointment : listOfAppointments) {

                if (eachAppointment.getAppointmentDateAndTime().isBefore(currentTimeAndDate)) {

                    listOfPastAppointments.add(eachAppointment);

                }

            }

        } catch (Exception exception) {

            System.out.println(exception.fillInStackTrace());

        }

        if (!listOfPastAppointments.isEmpty()) {

            return listOfPastAppointments;

        } else {

            throw new OptionalException("No past appointments. Sorry!");

        }
    }

    public static void testing() {
        int strength = 8;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        String encodedPassword = encoder.encode("1234");
    }

    @Override
    public List<Appointment> getFutureAppointment(Doctor doctor) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFutureAppointment'");
    }

    @Override
    public List<Appointment> getAllAppointments(Doctor registerDoctor) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAppointments'");
    }

    @Override
    public List<Patient> getListOfPatient() {
        List<Patient> listOfPatient = patientRepository.findAll();

        return listOfPatient;
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

    @Override
    public Session getCurrentUserByUuid(String uuid) throws OptionalException {
        Session userSession = sessionRepository.findByUuid(uuid);

        if (userSession != null) {

            return userSession;

        } else {

            throw new OptionalException("Please enter valid key");
        }
    }

    @Override
    public Receipt makeReviewToDoctorAppointment(String key, Receipt review) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeReviewToDoctorAppointment'");
    }

    @Override
    public Receipt updateReview(String key, Receipt review) throws OptionalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReview'");
    }

}
