package com.HMSApp.HospitalMngmnt.management;

import org.springframework.beans.factory.annotation.Autowired;

import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

public class SessionManager {
    @Autowired
    SessionRepository sessionRepository;

    public SessionManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSession(String sessionUUID) {
        return sessionRepository.findByUuid(sessionUUID);
    }

    public Session createSession(String sessionUUID, String userType) {
        Session session = new Session(sessionUUID, userType);
        return sessionRepository.save(session);
    }
}