package com.HMSApp.HospitalMngmnt.controller;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Forget;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.service.IDoctorService;
import com.HMSApp.HospitalMngmnt.service.IPatientOrAdminLoginService;
import com.HMSApp.HospitalMngmnt.service.IPatientService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PatientController {

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IPatientService patientService;

    @Autowired
    IPatientOrAdminLoginService adminLoginServiceProvider;

    @Autowired
    Session session;

    @CrossOrigin
    @PostMapping("/registerPatient")
    public ResponseEntity<Patient> savePatient(@Valid @RequestBody Patient patient) throws OptionalException {
        Patient savedPatient = patientService.createPatient(patient);

        return new ResponseEntity<Patient>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient,
            @RequestParam(required = false) String key) throws OptionalException {

        Patient updatedPatient = patientService.updatePatient(patient, key);

        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @GetMapping("/patients/{patientid}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer patientid) throws OptionalException {
        Patient patient = patientService.getPatientById(patientid);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/doctors")
    @CrossOrigin
    public ResponseEntity<List<Doctor>> getAllDoctorsFromDataBase(@RequestParam String key) throws OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            List<Doctor> returnedListOfDoctors = patientService.getAllDoctors();

            return new ResponseEntity<List<Doctor>>(returnedListOfDoctors, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }
    }

    @PostMapping("/bookAppointment")
    @CrossOrigin
    public ResponseEntity<Appointment> bookAppointment(@RequestParam String key, @RequestBody Appointment appointment)
            throws OptionalException, IOException {

        if (appointment == null) {
            throw new OptionalException("Please enter valid appointment");
        }

        if (adminLoginServiceProvider.isLogged(key)) {

            Appointment registerAppointment = patientService.bookAppointment(key, appointment);

            return new ResponseEntity<Appointment>(registerAppointment, HttpStatus.CREATED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }

    }

    @GetMapping("/allAppointment")
    @CrossOrigin
    public ResponseEntity<List<Appointment>> getAllAppointmenPatientWise(@RequestParam String key)
            throws OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            List<Appointment> listOfAppointments = patientService.getPatientAppointment(key);

            return new ResponseEntity<List<Appointment>>(listOfAppointments, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }

    }

    @PutMapping("/updateAppointment")
    @CrossOrigin
    public ResponseEntity<Appointment> updateAppointment(@RequestParam String key,
            @RequestBody Appointment newAppointment) throws OptionalException, IOException {

        if (adminLoginServiceProvider.isLogged(key)) {

            Appointment updatedAppointment = patientService.updateAppointment(key, newAppointment);

            return new ResponseEntity<Appointment>(updatedAppointment, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }

    }

    @GetMapping("/getAllDoctors")
    @CrossOrigin
    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String key) throws OptionalException {
        if (adminLoginServiceProvider.isLogged(key)) {

            List<Doctor> listOfDoctors = patientService.getAllDoctors();

            return new ResponseEntity<List<Doctor>>(listOfDoctors, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }
    }

    @DeleteMapping("/deleteAppointment")
    @CrossOrigin
    public ResponseEntity<Appointment> deleteAppointment(@RequestParam String key, @RequestBody Appointment appointment)
            throws OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            Appointment listOfDoctors = patientService.deleteAppointment(appointment);

            return new ResponseEntity<Appointment>(listOfDoctors, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }
    }

    @PutMapping("/forgetPassword")
    @CrossOrigin
    public ResponseEntity<Patient> forgetPassword(@RequestParam String key, @RequestBody Forget forgetPassword)
            throws OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            if (forgetPassword.getNewpassword().equals(forgetPassword.getConfirmnewpassword())) {

                if (forgetPassword.getOldpassword().equals(forgetPassword.getNewpassword())) {

                    throw new OptionalException("Please enter new password.");

                }

                Patient finalResult = patientService.forgetPassword(key, forgetPassword);

                return new ResponseEntity<Patient>(finalResult, HttpStatus.ACCEPTED);

            } else {

                throw new OptionalException("Please match both password. New password and confirm new password");

            }

        } else {

            throw new OptionalException("Invalid key or please login first");

        }
    }

    @PostMapping("/getReceipt")
    @CrossOrigin
    public ResponseEntity<Receipt> getReceiptOfParticularAppointment(@RequestParam String key,
            @RequestBody Appointment appointment)
            throws OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            Session currentUserSession = patientService.getUserByUuid(key);

            Patient registerPatient = patientService.getPatientByUuid(key);

            if (!currentUserSession.getUserType().equals("doctor")) {

                throw new OptionalException("Please login as doctor");

            }

            if (registerPatient != null) {

                Receipt receipt = patientService.getReceiptOfParticularAppointment(key, appointment);

                return new ResponseEntity<Receipt>(receipt, HttpStatus.OK);

            } else {

                throw new OptionalException("Please enter valid doctor details");

            }

        } else {

            throw new OptionalException("Please enter valid key");

        }

    }

    @PostMapping("/availableTiming")
    @CrossOrigin
    public ResponseEntity<List<LocalDateTime>> getAvailbleTimingOfDoctor(@RequestParam String key,
            @RequestBody Doctor doctor) throws IOException, OptionalException {

        if (adminLoginServiceProvider.isLogged(key)) {

            List<LocalDateTime> listOfAvailable = doctorService.getAvailableDoctorForBooking(key, doctor);

            return new ResponseEntity<List<LocalDateTime>>(listOfAvailable, HttpStatus.ACCEPTED);

        } else {

            throw new OptionalException("Invalid key or please login first");

        }
    }

}
