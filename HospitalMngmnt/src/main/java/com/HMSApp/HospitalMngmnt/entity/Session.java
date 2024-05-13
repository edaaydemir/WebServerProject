package com.HMSApp.HospitalMngmnt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@Entity
@ToString
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String uuid;

    private String userType;

    private LocalDateTime localDateTime;

    public Session() {
        // Default constructor (if needed)
    }

    public Session(Integer userId, String uuid, String userType, LocalDateTime localDateTime) {
        super();
        this.userId = userId;
        this.uuid = uuid;
        this.userType = userType;
        this.localDateTime = localDateTime;
    }
}
