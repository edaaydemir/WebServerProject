package com.HMSApp.HospitalMngmnt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.entity.Doctor;

import com.HMSApp.HospitalMngmnt.service.IDoctorPrivlageGiverService;
import com.HMSApp.HospitalMngmnt.service.IPatientOrAdminLoginService;
import com.HMSApp.HospitalMngmnt.service.IPatientService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	IDoctorPrivlageGiverService adminDoctorService;

	@Autowired
	IPatientOrAdminLoginService patientAndAdminLoginService;

	@Autowired
	IPatientService patientService;

	@PostMapping("/registerDoctor")
	@CrossOrigin
	public ResponseEntity<Doctor> registerDocotor(@RequestParam String key, @RequestBody Doctor doctor)
			throws OptionalException {

		if (patientAndAdminLoginService.isLogged(key)) {

			Session currentUserSession = patientService.getUserByUuid(key);

			if (!currentUserSession.getUserType().equals("admin")) {

				throw new OptionalException("Please login as admin");

			}

			if (doctor != null) {

				Doctor registerDoctor = adminDoctorService.registerDoctor(doctor);

				return new ResponseEntity<Doctor>(registerDoctor, HttpStatus.CREATED);

			} else {

				throw new OptionalException("Please enter valid doctor details");
			}

		} else {

			throw new OptionalException("Please enter valid key.");
		}

	}

	@DeleteMapping("/revokePermission")
	@CrossOrigin
	public ResponseEntity<Doctor> revokePermissionOfDoctor(@RequestParam String key, @RequestBody Doctor doctor)
			throws OptionalException {

		if (patientAndAdminLoginService.isLogged(key)) {

			Session currentUserSession = patientService.getUserByUuid(key);

			if (!currentUserSession.getUserType().equals("admin")) {

				throw new OptionalException("Please login as admin");

			}

			Doctor deletedDoctor = adminDoctorService.revokePermissionOfDoctor(doctor);

			return new ResponseEntity<Doctor>(deletedDoctor, HttpStatus.CREATED);

		} else {

			throw new OptionalException("Please enter valid key.");
		}
	}

	@PostMapping("/grantPermission")
	@CrossOrigin
	public ResponseEntity<Doctor> grantPermissionOfDoctor(@RequestParam String key, @RequestBody Doctor doctor)
			throws OptionalException {

		if (patientAndAdminLoginService.isLogged(key)) {

			Session currentUserSession = patientService.getUserByUuid(key);

			if (!currentUserSession.getUserType().equals("admin")) {

				throw new OptionalException("Please login as admin");

			}

			Doctor deletedDoctor = adminDoctorService.grantPermissionOfDoctor(doctor);

			return new ResponseEntity<Doctor>(deletedDoctor, HttpStatus.CREATED);

		} else {

			throw new OptionalException("Please enter valid key.");
		}
	}

	@GetMapping("/getValidInValidDoctors")
	@CrossOrigin
	public ResponseEntity<List<Doctor>> getAllValidInValidDoctors(@RequestParam String key)
			throws OptionalException {

		if (patientAndAdminLoginService.isLogged(key)) {

			Session currentUserSession = patientService.getUserByUuid(key);

			if (!currentUserSession.getUserType().equals("admin")) {

				throw new OptionalException("Please login as admin");

			}

			List<Doctor> listOfValidInValidDoctors = adminDoctorService.getAllValidDoctor(key);

			return new ResponseEntity<List<Doctor>>(listOfValidInValidDoctors, HttpStatus.CREATED);

		} else {

			throw new OptionalException("Please enter valid key.");
		}

	}

}
