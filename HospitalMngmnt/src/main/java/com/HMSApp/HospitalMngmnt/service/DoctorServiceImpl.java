package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.Patient;
import com.HMSApp.HospitalMngmnt.entity.Receipt;
import com.HMSApp.HospitalMngmnt.entity.Session;
import com.HMSApp.HospitalMngmnt.exception.OptionalException;
import com.HMSApp.HospitalMngmnt.repository.AppointmentRepository;
import com.HMSApp.HospitalMngmnt.repository.DoctorRepository;
import com.HMSApp.HospitalMngmnt.repository.PatientRepository;
import com.HMSApp.HospitalMngmnt.repository.ReceiptRepository;
import com.HMSApp.HospitalMngmnt.repository.SessionRepository;

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Override
    public List<Doctor> getAllDoctors() throws OptionalException {
        List<Doctor> allDocs = doctorRepository.findAll();

        if (!allDocs.isEmpty()) {
            return allDocs;
        }

        else {
            throw new OptionalException("Doctor yok");
        }

    }

    @Override
    public Doctor getDoctorById(Integer doctorid) throws OptionalException {
        return doctorRepository.findById(doctorid)
                .orElseThrow(() -> new RuntimeException("Bu idli doktor yok: " + doctorid));
    }

    @Override
    public Doctor getDoctorDetails(String key) throws OptionalException {
        Session currentDoctor = sessionRepository.findByUuid(key);

        Optional<Doctor> doctor = doctorRepository.findById(currentDoctor.getUserId());

        if (doctor.isPresent()) {

            return doctor.get();

        } else {

            throw new OptionalException("Doctor not present by this uuid " + key);
        }
    }

    @Override
    public List<LocalDateTime> getAvailableDoctorForBooking(String key, Doctor doctor)
            throws OptionalException, IOException {
        Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorid());

        List<LocalDateTime> doctorAvailableTiming = new ArrayList<>();

        if (registerDoctor.isPresent()) {

            PatientServiceImpl.getAppointmentDates(registerDoctor.get().getAvailableTimeFrom(),
                    registerDoctor.get().getAvailableTimeTo());

            Map<String, LocalDateTime> dateTimeMap = PatientServiceImpl.dateTimeMap;

            List<Appointment> listedDoc = registerDoctor.get().getListOfAppointments();

            for (String string : dateTimeMap.keySet()) {

                Boolean bool = false;

                for (Appointment eachAppointment : listedDoc) {

                    LocalDateTime localDateTime = dateTimeMap.get(string);

                    if (localDateTime.isEqual(eachAppointment.getAppointmentDateAndTime())) {

                        bool = true;
                        break;

                    }
                }

                if (bool == false) {

                    doctorAvailableTiming.add(dateTimeMap.get(string));

                }
            }

            if (!doctorAvailableTiming.isEmpty()) {

                return doctorAvailableTiming;

            } else {

                throw new OptionalException("MEvcut randevu yok");
            }

        } else {

            throw new OptionalException("Böyle bir doktoryok: " + doctor.getDoctorid());
        }
    }

    @Override
    public List<Appointment> getPastAppointment(Doctor doctor) throws OptionalException {
        List<Appointment> listOfAppointments = doctor.getListOfAppointments();

        List<Appointment> listOfPastAppointments = new ArrayList<>();

        LocalDateTime currentTimeAndDate = LocalDateTime.now();

        try {

            for (Appointment eachAppointment : listOfAppointments) {

                if (eachAppointment.getAppointmentDateAndTime().isBefore(currentTimeAndDate)) {

                    listOfPastAppointments.add(eachAppointment);

                }

            }

        } catch (Exception exception) {

            System.out.println(exception.fillInStackTrace());

        }

        if (!listOfPastAppointments.isEmpty()) {

            return listOfPastAppointments;

        } else {

            throw new OptionalException("No past appointments. Sorry!");

        }
    }

    @Override
    public List<Appointment> getFutureAppointment(Doctor doctor) throws OptionalException {
        List<Appointment> appointmentList = doctor.getListOfAppointments();
        List<Appointment> commingAppointments = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();

        try {
            for (Appointment appointment : appointmentList) {
                if (appointment.getAppointmentDateAndTime().isAfter(currentDate)) {
                    commingAppointments.add(appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("hatatrycatchdeki");
        }

        if (!commingAppointments.isEmpty()) {
            return commingAppointments;
        }

        else {
            throw new OptionalException("Gelecekte randevu yok");
        }

    }

    @Override
    public List<Appointment> getAllAppointments(Doctor registerDoctor) throws OptionalException {
        List<Appointment> appointmentList = registerDoctor.getListOfAppointments();

        if (appointmentList.isEmpty()) {
            return appointmentList;
        }

        else {
            throw new OptionalException("Randevu mevcut değil");
        }

    }

    @Override
    public List<Patient> getListOfPatient() {
        List<Patient> listOfPatient = patientRepository.findAll();

        return listOfPatient;
    }

    @Override
    public Receipt getReceiptOfDoctorByPatient(String key, Receipt receipt) throws OptionalException {
        Session currentDocSession = sessionRepository.findByUuid(key);

        Optional<Doctor> registerDoc = doctorRepository.findById(currentDocSession.getUserId());

        if (registerDoc.isPresent()) {

            Optional<Patient> registerPatient = patientRepository.findById(receipt.getPatient().getPatientid());

            if (registerPatient.isEmpty()) {

                throw new OptionalException("No patient found with this id " + receipt.getPatient().getPatientid());

            }

            Optional<Appointment> registerAppointment = appointmentRepository
                    .findById(receipt.getAppointment().getAppointmentId());

            if (registerAppointment.isEmpty()) {

                throw new OptionalException("No appointment found with this id");

            }

            Receipt receipt2 = registerAppointment.get().getReceipt();

            if (receipt2 != null) {

                return receipt2;

            } else {

                throw new OptionalException("No review found");

            }
        }

        else {
            throw new OptionalException("Böyle bir hasta yok");
        }

    }

    @Override
    public Receipt deleteReceipt(String key, Receipt receipt) throws OptionalException {
        Session currentDocSession = sessionRepository.findByUuid(key);

        Optional<Doctor> registerDocOptional = doctorRepository.findById(currentDocSession.getUserId());

        List<Receipt> receiptList = registerDocOptional.get().getListReceipts();

        Boolean chkReceiptisPresentinDoctorReceiptList = false;

        for (Receipt receiptEach : receiptList) {

            int result = receiptEach.getReceiptid().compareTo(receipt.getReceiptid());

            if (result == 0) {

                chkReceiptisPresentinDoctorReceiptList = true;

                break;
            }
        }

        Optional<Receipt> registerReceipt = receiptRepository.findById(receipt.getReceiptid());

        if (registerReceipt.isPresent() && chkReceiptisPresentinDoctorReceiptList) {
            registerDocOptional.get().getListReceipts().remove(registerReceipt.get());
            doctorRepository.save(registerDocOptional.get());

            Patient regPatient = registerReceipt.get().getPatient();
            regPatient.getListReceipts().remove(registerReceipt.get());
            patientRepository.save(regPatient);

            Appointment registerAppointment = registerReceipt.get().getAppointment();
            registerAppointment.setReceipt(null);
            appointmentRepository.save(registerAppointment);
            receiptRepository.delete(registerReceipt.get());
            return registerReceipt.get();

        }

        else {
            throw new OptionalException("Böyle bir reçete yok:" + receipt.getReceiptid());
        }

    }

    @Override
    public Session getCurrentUserByUuid(String uuid) throws OptionalException {
        Session userSession = sessionRepository.findByUuid(uuid);

        if (userSession != null) {

            return userSession;

        } else {

            throw new OptionalException("Please enter valid key");
        }
    }

    @Override
    public Receipt makeReceiptToDoctorAppointment(String key, Receipt receipt) throws OptionalException {
        Optional<Appointment> registerAppointment = appointmentRepository
                .findById(receipt.getAppointment().getAppointmentId());

        if (registerAppointment.isPresent()) {
            Session currnetDocSession = sessionRepository.findByUuid(key);

            Optional<Doctor> registerDoctor = doctorRepository.findById(currnetDocSession.getUserId());

            if (registerAppointment.get().getDoctor().getDoctorid() == registerDoctor.get().getDoctorid()) {

                Optional<Patient> registerPatient = patientRepository.findById(receipt.getPatient().getPatientid());

                if (registerPatient.isPresent()) {

                    LocalDateTime appointmentDate = registerAppointment.get().getAppointmentDateAndTime().plusHours(1);

                    LocalDateTime presentTime = LocalDateTime.now();

                    if (appointmentDate.isBefore(presentTime)) {

                        Receipt registerReview = registerAppointment.get().getReceipt();

                        if (registerReview == null) {

                            receipt.setAppointment(registerAppointment.get());
                            receipt.setDoctor(registerDoctor.get());
                            receipt.setPatient(registerPatient.get());

                            Receipt registerReceipt2 = receiptRepository.save(receipt);

                            // map review to doctor
                            registerDoctor.get().getListReceipts().add(registerReceipt2);
                            doctorRepository.save(registerDoctor.get());

                            // map review to patient
                            registerPatient.get().getListReceipts().add(registerReceipt2);
                            patientRepository.save(registerPatient.get());

                            // map review to appointment
                            registerAppointment.get().setReceipt(registerReceipt2);
                            appointmentRepository.save(registerAppointment.get());

                            return registerReceipt2;

                        } else {

                            throw new OptionalException("Receipt already present please edit receipt");
                        }

                    } else {

                        throw new OptionalException("Please make sure appointment is over or not. Try again later.");

                    }

                } else {

                    throw new OptionalException(
                            "Patient not found with this id " + receipt.getPatient().getPatientid());
                }

            } else {

                throw new OptionalException("Please enter valid patient in appointment");
            }

        } else {
            throw new OptionalException("This appointment does not exist");
        }

    }

    @Override
    public Receipt updateReceipt(String key, Receipt receipt) throws OptionalException {
        Optional<Receipt> registerReceipt = receiptRepository.findById(receipt.getReceiptid());

        if (registerReceipt.isPresent()) {
            return receiptRepository.save(receipt);
        }

        else {
            throw new OptionalException("Böyle bir reçete yok");
        }
    }

    @Override
    public Doctor getDoctorByUuid(String uuid) throws OptionalException {
        Session currentDoctor = sessionRepository.findByUuid(uuid);

        Optional<Doctor> doctor = doctorRepository.findById(currentDoctor.getUserId());

        if (doctor.isPresent()) {

            return doctor.get();

        } else {

            throw new OptionalException("Doctor not present by this uuid " + uuid);
        }
    }

}
