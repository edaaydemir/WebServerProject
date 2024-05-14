package com.HMSApp.HospitalMngmnt.service;

import com.HMSApp.HospitalMngmnt.entity.Login;
import com.HMSApp.HospitalMngmnt.entity.LoginUserKey;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;

public interface IPatientOrAdminLoginService {

    LoginUserKey login(Login login) throws OptionalException;

    String logout(String key) throws OptionalException;

    Boolean isLogged(String key) throws OptionalException;

}
