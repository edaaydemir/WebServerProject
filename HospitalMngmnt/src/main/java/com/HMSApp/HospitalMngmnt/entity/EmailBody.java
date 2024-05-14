package com.HMSApp.HospitalMngmnt.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class EmailBody {

    String emailText;
    String emailHeader;

}
