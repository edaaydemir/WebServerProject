package com.HMSApp.HospitalMngmnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;

import com.HMSApp.HospitalMngmnt.service.IDoctorLoginService;

import com.HMSApp.HospitalMngmnt.service.IDoctorService;

@RestController
public class DoctorController {

	@Autowired
	IDoctorLoginService doctorLoginService;

	@Autowired
	IDoctorService doctorService;

	@GetMapping("/getDoctorDetails")
	@CrossOrigin
	public ResponseEntity<Doctor> getDoctorDetails(@RequestParam String key) throws OptionalException {

		if (doctorLoginService.isLoggedIn(key)) {

			Doctor returnDoctor = doctorService.getDoctorDetails(key);

			return new ResponseEntity<Doctor>(returnDoctor, HttpStatus.ACCEPTED);

		} else {

			throw new OptionalException("Please enter valid key");

		}
	}

	@GetMapping("/upcomingAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getUpcomingAppointments(@RequestParam String key)
			throws OptionalException {

		if (doctorLoginService.isLoggedIn(key)) {

			Session currentUserSession = doctorService.getCurrentUserByUuid(key);

			Doctor registerDoctor = doctorService.getDoctorByUuid(key);

			if (!currentUserSession.getUserType().equals("doctor")) {

				throw new OptionalException("Please login as doctor");

			}

			if (registerDoctor != null) {

				List<Appointment> listOfUpCommingAppointment = doctorService
						.getFutureAppointment(registerDoctor);

				return new ResponseEntity<List<Appointment>>(listOfUpCommingAppointment, HttpStatus.ACCEPTED);

			} else {

				throw new OptionalException("Please enter valid doctor details");

			}

		} else {

			throw new OptionalException("Please enter valid key");

		}

	}

	@GetMapping("/pastAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getPastAppointments(@RequestParam String key)
			throws OptionalException {

		if (doctorLoginService.isLoggedIn(key)) {

			Session currentUserSession = doctorService.getCurrentUserByUuid(key);

			Doctor registerDoctor = doctorService.getDoctorByUuid(key);

			if (!currentUserSession.getUserType().equals("doctor")) {

				throw new OptionalException("Please login as doctor");

			}

			if (registerDoctor != null) {

				List<Appointment> listOfUpPastAppointment = doctorService.getPastAppointment(registerDoctor);

				return new ResponseEntity<List<Appointment>>(listOfUpPastAppointment, HttpStatus.ACCEPTED);

			} else {

				throw new OptionalException("Please enter valid doctor details");

			}

		} else {

			throw new OptionalException("Please enter valid key");

		}

	}

	@GetMapping("/getAllAppointments")
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getAllAppointments(@RequestParam String key)
			throws OptionalException {

		if (doctorLoginService.isLoggedIn(key)) {

			Session currentUserSession = doctorService.getCurrentUserByUuid(key);

			Doctor registerDoctor = doctorService.getDoctorByUuid(key);

			if (!currentUserSession.getUserType().equals("doctor")) {

				throw new OptionalException("Please login as doctor");

			}

			if (registerDoctor != null) {

				List<Appointment> listOfUpPastAppointment = doctorService.getAllAppointments(registerDoctor);

				return new ResponseEntity<List<Appointment>>(listOfUpPastAppointment, HttpStatus.ACCEPTED);

			} else {

				throw new OptionalException("Please enter valid doctor details");

			}

		} else {

			throw new OptionalException("Please enter valid key");

		}

	}

	@GetMapping("/listOfPatient")
	@CrossOrigin
	public ResponseEntity<List<Patient>> getAllListOfPatient(@RequestParam String key)
			throws OptionalException {

		if (doctorLoginService.isLoggedIn(key)) {

			Session currentUserSession = doctorService.getCurrentUserByUuid(key);

			Doctor registerDoctor = doctorService.getDoctorByUuid(key);

			if (!currentUserSession.getUserType().equals("doctor")) {

				throw new OptionalException("Please login as doctor");

			}

			if (registerDoctor != null) {

				List<Patient> listOfPatient = doctorService.getListOfPatient();

				return new ResponseEntity<List<Patient>>(listOfPatient, HttpStatus.OK);

			} else {

				throw new OptionalException("Please enter valid doctor details");

			}

		} else {

			throw new OptionalException("Please enter valid key");

		}
	}

}
