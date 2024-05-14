package com.HMSApp.HospitalMngmnt.service;

import java.util.List;

import com.HMSApp.HospitalMngmnt.entity.Doctor;

import com.HMSApp.HospitalMngmnt.exception.OptionalException;

public interface IDoctorPrivlageGiverService {

	Doctor registerDoctor(Doctor doctor) throws OptionalException;

	Doctor revokePermissionOfDoctor(Doctor doctor) throws OptionalException;

	Doctor grantPermissionOfDoctor(Doctor doctor) throws OptionalException;

	List<Doctor> getAllValidDoctor(String key) throws OptionalException;

}
