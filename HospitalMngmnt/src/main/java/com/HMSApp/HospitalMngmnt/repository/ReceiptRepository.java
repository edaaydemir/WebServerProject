package com.HMSApp.HospitalMngmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.HospitalMngmnt.entity.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

}
