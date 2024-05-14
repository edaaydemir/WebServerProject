package com.HMSApp.HospitalMngmnt.service;

import java.io.IOException;
import java.security.SecureRandom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import com.HMSApp.HospitalMngmnt.entity.Appointment;
import com.HMSApp.HospitalMngmnt.entity.Doctor;
import com.HMSApp.HospitalMngmnt.entity.EmailBody;
import com.HMSApp.HospitalMngmnt.entity.Forget;
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
public class PatientServiceImpl implements IPatientService, Runnable {

    public static Map<String, LocalDateTime> dateTimeMap = new LinkedHashMap<>();

    public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(7, new SecureRandom());

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository userRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    Appointment appointmentsave;

    @Autowired
    EmailBody emailBody;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    IEmailService emailService;

    @Autowired
    SessionRepository sessionRepository;

    public PatientServiceImpl(Appointment appointment, IEmailService emailService, EmailBody emailBody) {

        this.appointmentsave = appointment;
        this.emailService = emailService;
        this.emailBody = emailBody;

    }

    @Override
    public Patient getPatientById(Integer patientid) {
        return patientRepository.findById(patientid)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientid));
    }

    @Override
    public Patient createPatient(Patient patient) throws OptionalException {
        Patient dbUser = userRepository.findByEmail(patient.getEmail());

        if (dbUser == null) {

            patient.setType("Patient");

            patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));

            userRepository.save(patient);

            return patient;
        }

        else {
            throw new OptionalException("Already exits a patient using this email:" + patient.getEmail());
        }

    }

    @Override
    public Patient updatePatient(Patient user, String key) throws OptionalException {
        Session loggedInUser = sessionRepository.findByUuid(key);

        if (loggedInUser == null) {

            throw new OptionalException("Please provide the valid key to update the user");
        }

        if (user.getPatientid() == loggedInUser.getUserId()) {

            return userRepository.save(user);

        } else {
            throw new OptionalException("Invalid user details. Login first");
        }
    }

    public static void getAppointmentDates(Integer from, Integer to) throws IOException, OptionalException {

        dateTimeMap.clear();

        if (from == null || to == null) {
            throw new OptionalException("Please enter valid doctor appointment From to To time");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime tomorrowDateTime = currentDateTime.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int i = from; i <= to; i++) {
            String todayTimeString = currentDateTime.toLocalDate() + " " + (i < 10 ? "0" + i : i) + ":00";
            LocalDateTime todayDateTime = LocalDateTime.parse(todayTimeString, formatter);
            if (currentDateTime.isBefore(todayDateTime)) {
                dateTimeMap.put("today" + i, todayDateTime);
            }
        }

        for (int i = from; i <= to; i++) {
            String tomorrowTimeString = tomorrowDateTime.toLocalDate() + " " + (i < 10 ? "0" + i : i) + ":00";
            LocalDateTime tomorrowDateTimeParsed = LocalDateTime.parse(tomorrowTimeString, formatter);
            if (currentDateTime.isBefore(tomorrowDateTimeParsed)) {
                dateTimeMap.put("tomorrow" + i, tomorrowDateTimeParsed);
            }
        }
    }

    @Override
    public Appointment bookAppointment(String key, Appointment appointment) throws IOException, OptionalException {
        Session patientSession = sessionRepository.findByUuid(key);

        Optional<Patient> patient = patientRepository.findById(patientSession.getUserId());

        synchronized (this) {

            if (patient.isPresent()) {

                appointment.setPatient(patient.get());

                Doctor doctor = appointment.getDoctor();

                Optional<Doctor> registerDoctors = doctorRepository.findById(doctor.getDoctorid());

                if (!registerDoctors.isEmpty()) {

                    appointment.setDoctor(registerDoctors.get());

                    getAppointmentDates(registerDoctors.get().getAvailableTimeFrom(),
                            registerDoctors.get().getAvailableTimeTo());

                    List<Appointment> listOfAppointment = appointment.getDoctor().getListOfAppointments();

                    Boolean bool1 = false;

                    Boolean bool2 = false;

                    for (Appointment eachAppointment : listOfAppointment) {

                        if (eachAppointment.getAppointmentDateAndTime()
                                .isEqual(appointment.getAppointmentDateAndTime())) {

                            bool1 = true;

                        }
                    }

                    for (String str : dateTimeMap.keySet()) {

                        if (dateTimeMap.get(str).isEqual(appointment.getAppointmentDateAndTime())) {
                            bool2 = true;
                        }
                    }

                    Appointment registerAppointment = null;

                    if (!bool1 && bool2) {

                        registerAppointment = appointmentRepository.save(appointment);

                        emailBody.setEmailText("Dear Patient, \n You just booked an appointment with: "
                                + registerAppointment.getDoctor().getDoctorname() +
                                ". Please make sure to join on time. If you want to call a doctor please contact "
                                + registerAppointment.getDoctor().getEmail() + "\n"

                                + "\n"
                                + "Appointment Id: " + registerAppointment.getAppointmentId() + "\n"
                                + "Doctor specialty: " + registerAppointment.getDoctor().getSpecialization() + "\n"
                                + "Doctor experience: " + registerAppointment.getDoctor().getExperience() + "\n"
                                + "\n"

                                + "Thanks and Regards \n"
                                + "Appointment Booking Application");

                        emailBody.setEmailHeader("You have successfully book appointment at "
                                + registerAppointment.getAppointmentDateAndTime());

                        PatientServiceImpl patientServiceImpl = new PatientServiceImpl(appointment, emailService,
                                emailBody);

                        Thread emailSentThread = new Thread(patientServiceImpl);

                        emailSentThread.start();

                    } else {

                        throw new OptionalException(
                                "This time or date already booked or please enter valid appointment time and date "
                                        + appointment.getAppointmentDateAndTime());
                    }

                    registerDoctors.get().getListOfAppointments().add(registerAppointment);

                    doctorRepository.save(registerDoctors.get());

                    patient.get().getListOfAppointments().add(registerAppointment);

                    patientRepository.save(patient.get());

                    return registerAppointment;

                } else {

                    throw new OptionalException("Please enter valid doctors details or doctor not present with thid id "
                            + doctor.getDoctorid());

                }

            } else {

                throw new OptionalException("Please enter valid key");

            }

        }
    }

    @Override
    public Appointment updateAppointment(String key, Appointment newAppointment) throws IOException, OptionalException {
        Session patientSession = sessionRepository.findByUuid(key);

        Optional<Patient> patient = patientRepository.findById(patientSession.getUserId());

        if (patient.get() != null) {
            Optional<Appointment> registerAppoinment = appointmentRepository
                    .findById(newAppointment.getAppointmentId());

            Optional<Doctor> registerDoctor = doctorRepository.findById(newAppointment.getDoctor().getDoctorid());

            if (!registerAppoinment.isEmpty()) {

                Doctor newDoctor = newAppointment.getDoctor();
                Doctor oldDoctor = registerAppoinment.get().getDoctor();

                Boolean isDoctorChanged = newDoctor.getDoctorid() == oldDoctor.getDoctorid() ? true : false;

                if (!registerDoctor.isEmpty()) {

                    LocalDateTime newTime = newAppointment.getAppointmentDateAndTime();
                    LocalDateTime oldTime = registerAppoinment.get().getAppointmentDateAndTime();

                    if (!newTime.isEqual(oldTime) || !isDoctorChanged) {

                        LocalDateTime presentTime = LocalDateTime.now();

                        if (oldTime.isBefore(presentTime) && !newTime.isAfter(presentTime)) {

                            throw new OptionalException(
                                    "You can't update the expired appointment.");

                        }

                        getAppointmentDates(registerDoctor.get().getAvailableTimeFrom(),
                                registerDoctor.get().getAvailableTimeTo());

                        List<Appointment> listOfAppointment = registerDoctor.get().getListOfAppointments();

                        Boolean bool1 = false;

                        Boolean bool2 = false;

                        for (Appointment eachAppointment : listOfAppointment) {

                            if (eachAppointment.getAppointmentDateAndTime()
                                    .isEqual(newAppointment.getAppointmentDateAndTime())) {

                                bool1 = true;

                            }
                        }

                        for (String str : dateTimeMap.keySet()) {

                            if (dateTimeMap.get(str).isEqual(newAppointment.getAppointmentDateAndTime())) {

                                bool2 = true;

                            }
                        }

                        if (!bool1 && bool2) {
                            registerAppoinment.get().getDoctor().getListOfAppointments().remove(newAppointment);
                            appointmentRepository.save(registerAppoinment.get());
                            newAppointment.setDoctor(registerDoctor.get());
                            registerDoctor.get().getListOfAppointments().add(newAppointment);
                            doctorRepository.save(registerDoctor.get());
                            return newAppointment;

                        } else {

                            throw new OptionalException(
                                    "This time or date occupied. Please enter another appointment time and date "
                                            + newAppointment.getAppointmentDateAndTime());

                        }

                    } else {

                        throw new OptionalException("Please update the appointment. You did not update anythings.");
                    }

                } else {

                    throw new OptionalException(
                            "No doctor found with this id " + newAppointment.getDoctor().getDoctorid());
                }

            } else {

                throw new OptionalException("No appointments found. Please book appointments");
            }

        } else {

            throw new OptionalException("Please enter valid patient details");
        }
    }

    @Override
    public Appointment deleteAppointment(Appointment appointment) throws OptionalException {

        Optional<Appointment> registerAppointment = appointmentRepository.findById(appointment.getAppointmentId());

        LocalDateTime localDateTime = LocalDateTime.now();

        if (localDateTime.isAfter(registerAppointment.get().getAppointmentDateAndTime())) {

            throw new OptionalException("Appointment time already passed.");

        }

        // check appointment is exist or not
        if (registerAppointment.isPresent()) {

            // check doctor is exist or not
            Optional<Doctor> registerDoctor = doctorRepository.findById(appointment.getDoctor().getDoctorid());

            if (registerDoctor.isPresent()) {

                Optional<Patient> registerPatient = patientRepository.findById(appointment.getPatient().getPatientid());

                // check patient is exist or not
                if (registerPatient.isPresent()) {

                    Boolean doctorListFlag = registerDoctor.get().getListOfAppointments()
                            .remove(registerAppointment.get());

                    Boolean patientListFlag = registerPatient.get().getListOfAppointments()
                            .remove(registerAppointment.get());

                    if (doctorListFlag && patientListFlag) {

                        appointmentRepository.delete(registerAppointment.get());

                        // sending mail to patient for successfully canceling booking of appointment

                        emailBody.setEmailText("Dear Sir/Ma'am, \n You have canceled an appointment with "
                                + registerAppointment.get().getDoctor().getDoctorname()

                                + "\n"
                                + "Appointment Id: " + registerAppointment.get().getAppointmentId() + "\n"
                                + "Doctor specialty: " + registerAppointment.get().getDoctor().getSpecialization()
                                + "\n"
                                + "Doctor experience : " + registerAppointment.get().getDoctor().getExperience() + "\n"
                                + "\n"

                                + "Thanks and Regards \n"
                                + "Appointment Booking Application");

                        emailBody.setEmailHeader("Cancel Appointment Booking: "
                                + appointment.getAppointmentDateAndTime() + " successfully");

                        PatientServiceImpl patientServiceImpl = new PatientServiceImpl(registerAppointment.get(),
                                emailService, emailBody);

                        Thread emailSentThread = new Thread(patientServiceImpl);

                        emailSentThread.start();

                        return registerAppointment.get();

                    } else {

                        throw new OptionalException("Something went wrong. Appointment did not cancel");

                    }

                } else {

                    throw new OptionalException(
                            "No patient found with this id " + appointment.getPatient().getPatientid());
                }

            } else {

                throw new OptionalException("No doctor found with this id " + appointment.getDoctor().getDoctorid());
            }

        } else {

            throw new OptionalException("Appointment did not found " + appointment.getAppointmentId());
        }

    }

    @Override
    public List<Appointment> getPatientAppointment(String key) throws OptionalException {
        Session patientSession = sessionRepository.findByUuid(key);

        Optional<Patient> patient = patientRepository.findById(patientSession.getUserId());

        if (patient.get() != null) {

            List<Appointment> listOfAppointments = patient.get().getListOfAppointments();

            if (!listOfAppointments.isEmpty()) {

                return listOfAppointments;

            } else {

                throw new OptionalException("No exists such appointments. Please make appointments");
            }

        } else {

            throw new OptionalException("Please enter valid patient details");
        }

    }

    @Override
    public List<Doctor> getAllDoctors() throws OptionalException {

        List<Doctor> listedDoc = doctorRepository.findAll();

        // Filter out invalid doctors
        List<Doctor> validDoctors = listedDoc.stream()
                .filter(doctor -> doctor.getIsValid() == true)
                .collect(Collectors.toList());

        if (!validDoctors.isEmpty()) {
            return validDoctors;
        } else {
            throw new OptionalException("There are no valid doctors currently available");
        }
    }

    @Override
    public Patient forgetPassword(String key, Forget forgetPassword) throws OptionalException {

        Session Session = sessionRepository.findByUuid(key);
        Optional<Patient> registerPatient = patientRepository.findById(Session.getUserId());
        // Check if the old password matches the current password
        boolean isMatched = bCryptPasswordEncoder.matches(forgetPassword.getOldpassword(),
                registerPatient.get().getPassword());

        if (isMatched) {
            // Update the password with the new one
            registerPatient.get().setPassword(bCryptPasswordEncoder.encode(forgetPassword.getNewpassword()));
            // Save the updated patient
            return patientRepository.save(registerPatient.get());
        } else {
            // Throw exception if the old password does not match
            throw new OptionalException("The old password does not match");
        }
    }

    @Override
    public Session getUserByUuid(String uuid) throws OptionalException {

        Session session = sessionRepository.findByUuid(uuid);

        if (session != null) {
            return session;
        }

        else {
            throw new OptionalException("ID not found");
        }

    }

    @Override
    public void run() {

        try {
            emailService.sendBookingMail(appointmentsave.getPatient().getEmail(), emailBody);

        } catch (MessagingException e) {

            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatientByUuid(String uuid) throws OptionalException {
        Session currentPatient = sessionRepository.findByUuid(uuid);

        Optional<Patient> patient = userRepository.findById(currentPatient.getUserId());

        if (patient.isPresent()) {

            return patient.get();

        } else {

            throw new OptionalException("Patient or Admin not present by this uuid " + uuid);
        }
    }

    @Override
    public Receipt getReceiptOfParticularAppointment(String key, Appointment appointment) throws OptionalException {
        Session currentPatient = sessionRepository.findByUuid(key);

        Optional<Patient> registerPatient = patientRepository.findById(currentPatient.getUserId());

        if (registerPatient.isPresent()) {

            List<Receipt> listOfReceipt = registerPatient.get().getListReceipts();

            if (!listOfReceipt.isEmpty()) {

                for (Receipt eachReceipt : listOfReceipt) {

                    if (eachReceipt.getAppointment().getAppointmentId() == appointment.getAppointmentId()) {

                        return eachReceipt;
                    }
                }

                throw new OptionalException("No review found");

            } else {

                throw new OptionalException("No review found with this doctor");

            }
        } else {

            throw new OptionalException("Doctor not present by this uuid " + key);
        }
    }
}
