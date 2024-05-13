package com.HMSApp.HospitalMngmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.HospitalMngmnt.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    public Session findByUuid(String uuid);

}
