package com.HMSApp.HospitalMngmnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.HospitalMngmnt.entity.Login;
import com.HMSApp.HospitalMngmnt.entity.LoginCheck;
import com.HMSApp.HospitalMngmnt.entity.LoginUserKey;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.service.IDoctorLoginService;
import com.HMSApp.HospitalMngmnt.service.IPatientOrAdminLoginService;

@RestController
public class LoginController {

	@Autowired
	private IPatientOrAdminLoginService patientAndAdminLoginService;

	@Autowired
	private IDoctorLoginService doctorLoginService;

	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<LoginUserKey> loginPatient(@RequestBody Login login) throws OptionalException {

		LoginUserKey result = patientAndAdminLoginService.login(login);

		return new ResponseEntity<LoginUserKey>(result, HttpStatus.OK);

	}

	@PostMapping("/loginDoctor")
	@CrossOrigin
	public ResponseEntity<LoginUserKey> loginDoctor(@RequestBody Login login) throws OptionalException {

		LoginUserKey result = doctorLoginService.logIn(login);

		return new ResponseEntity<LoginUserKey>(result, HttpStatus.OK);

	}

	@CrossOrigin
	@GetMapping("/checkLogin/{uuid}")
	public ResponseEntity<LoginCheck> isLogged(@PathVariable String uuid) throws OptionalException {

		Boolean loginResult = patientAndAdminLoginService.isLogged(uuid);

		LoginCheck loginCheck = new LoginCheck(loginResult);

		return new ResponseEntity<LoginCheck>(loginCheck, HttpStatus.OK);

	}

	@PostMapping("/logout")
	@CrossOrigin
	public String logoutPatient(@RequestParam(required = false) String key) throws OptionalException {

		return patientAndAdminLoginService.logout(key);

	}

	@DeleteMapping("/logoutDoctor")
	public String logoutDoctor(@RequestParam(required = false) String key) throws OptionalException {

		return doctorLoginService.logOut(key);

	}

}
