package com.HMSApp.HospitalMngmnt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.AppointmentRepository;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;

@Service
public class DoctorPrivlageGiverServiceImpl implements IDoctorPrivlageGiverService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Doctor registerDoctor(Doctor doctor) throws OptionalException {

        Doctor dbDoctor = doctorRepository.findByEmail(doctor.getEmail());

        if (dbDoctor == null) {

            doctor.setType("Doctor");

            doctor.setPassword(PatientServiceImpl.bCryptPasswordEncoder.encode(doctor.getPassword()));

            return doctorRepository.save(doctor);

        } else {

            throw new OptionalException("There exists no doctor with email");
        }
    }

    @Override
    public Doctor revokePermissionOfDoctor(Doctor doctor) throws OptionalException {

        Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorid());

        if (registerDoctor.isPresent()) {

            registerDoctor.get().setIsValid(false);

            return doctorRepository.save(registerDoctor.get());

        } else {
            throw new OptionalException("Bu idli doctor yok");
        }
    }

    @Override
    public Doctor grantPermissionOfDoctor(Doctor doctor) throws OptionalException {
        Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorid());

        if (registerDoctor.isPresent()) {

            registerDoctor.get().setIsValid(true);

            return doctorRepository.save(registerDoctor.get());

        } else {

            throw new OptionalException("Doctor not present with this id " + doctor.getDoctorid());

        }
    }

    @Override
    public List<Doctor> getAllValidDoctor(String key) throws OptionalException {

        List<Doctor> listOfDoctors = doctorRepository.findAll();

        if (!listOfDoctors.isEmpty()) {

            return listOfDoctors;

        } else {

            throw new OptionalException("Doktor bulunamadı");

        }
    }

}
