package com.HMSApp.HospitalMngmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.HospitalMngmnt.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
