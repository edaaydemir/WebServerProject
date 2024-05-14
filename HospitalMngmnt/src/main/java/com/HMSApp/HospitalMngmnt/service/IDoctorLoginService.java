package com.HMSApp.HospitalMngmnt.service;

import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Login;
import com.HMSApp.HospitalMngmnt.entity.LoginUserKey;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;

@Service
public interface IDoctorLoginService {

    LoginUserKey logIn(Login login) throws OptionalException;

    String logOut(String key) throws OptionalException;

    Boolean isLoggedIn(String key) throws OptionalException;

}
