package com.HMSApp.HospitalMngmnt.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer receiptid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientid")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "receipt")
    private Appointment appointment;

    private String receiptText;

    @Override
    public int hashCode() {
        return Objects.hash(receiptid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Receipt other = (Receipt) obj;
        return Objects.equals(receiptid, other.receiptid);
    }

}
