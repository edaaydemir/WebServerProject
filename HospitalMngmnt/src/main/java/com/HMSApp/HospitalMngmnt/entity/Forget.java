package com.HMSApp.HospitalMngmnt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Forget {

    private String oldpassword;

    private String newpassword;

    private String confirmnewpassword;

}
