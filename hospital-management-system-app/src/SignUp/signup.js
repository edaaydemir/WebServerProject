import React, { useEffect, useState } from "react";
import Navbar from "../navbar";
import Footer from "../footer";
import { useNavigate } from "react-router-dom"; // useHistory yerine useNavigate kullanıyoruz
import axios from "axios";

const Signup = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    patientname: "",
    patientsurname: "",
    age: "",
    phoneNum: "",
    gender: "",
    dateOfBorn: "",
    email: "",
    password: "",
    city: "",
    status: "",
  });

  const [phoneNumberError, setPhoneNumberError] = useState(false);
  const [passwordMatchError, setPasswordMatchError] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });

    if (name === "phoneNum") {
      // Validate phone number
    } else if (name === "confirmPassword") {
      // Validate password match
      if (value !== formData.password) {
        setPasswordMatchError(true);
      } else {
        setPasswordMatchError(false);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!phoneNumberError && !passwordMatchError) {
      try {
        const response = await axios.post(
          "http://localhost:8080/registerPatient",
          formData
        );
        console.log("User registered:", response.data);
        navigate("/homepage");
      } catch (error) {
        console.error("Error registering user:", error);
      }
    } else {
      console.log("Form validation failed.");
    }
  };

  const formatDate = (date) => {
    const d = new Date(date);
    const year = d.getFullYear();
    let month = `${d.getMonth() + 1}`;
    let day = `${d.getDate()}`;

    if (month.length < 2) month = `0${month}`;
    if (day.length < 2) day = `0${day}`;

    return [year, month, day].join("-");
  };

  return (
    <div>
      {" "}
      <Navbar></Navbar>
      <div style={styles.container}>
        <div style={styles.form}>
          <h2 style={styles.title}>SIGN UP</h2>
          <form onSubmit={handleSubmit}>
            <div style={styles.inputGroup}>
              <label htmlFor="patientname">Patient Name:</label>
              <input
                type="text"
                id="patientname"
                name="patientname"
                value={formData.patientname}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="patientsurname">Patient Surname:</label>
              <input
                type="text"
                id="patientsurname"
                name="patientsurname"
                value={formData.patientsurname}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="age">Age:</label>
              <input
                type="number"
                id="age"
                name="age"
                value={formData.age}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="phoneNum">Phone Number:</label>
              <input
                type="text"
                id="phoneNum"
                name="phoneNum"
                value={formData.phoneNum}
                onChange={handleChange}
                required
              />
              {phoneNumberError && (
                <span>
                  Please enter a valid phone number starting with +90 and
                  containing exactly 10 digits.
                </span>
              )}
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="gender">Gender:</label>
              <input
                type="text"
                id="gender"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="dateOfBorn">Date of Birth:</label>
              <input
                type="date"
                id="dateOfBorn"
                name="dateOfBorn"
                value={formData.dateOfBorn}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="password">Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="city">City:</label>
              <input
                type="text"
                id="city"
                name="city"
                value={formData.city}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="status">Status:</label>
              <input
                type="text"
                id="status"
                name="status"
                value={formData.status}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit">Sign Up</button>
          </form>
        </div>
      </div>
      <Footer></Footer>
    </div>
  );
};

const styles = {
  container: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",

    height: "90vh",
    marginTop: "30px",
    marginBottom: "130px",
  },
  form: {
    width: "400px",
    padding: "20px",
    backgroundColor: "#f5f5f5",
    borderRadius: "10px",
    boxShadow: "0px 0px 10px 0px rgba(0, 0, 0, 0.1)",
    textAlign: "center",
  },
  title: {
    color: "#424242",
    marginBottom: "10px",
  },
  inputGroup: {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: "5px",
  },
  label: {
    color: "#424242",
    marginRight: "10px",
    minWidth: "120px",
    textAlign: "left",
  },
  input: {
    flex: "1",
    padding: "10px",
    border: "1px solid #424242",
    borderRadius: "5px",
    maxWidth: "200px",
  },
  signUp: {
    marginBottom: "20px",
  },
  button: {
    padding: "10px 20px",
    backgroundColor: "#f5f5f5", // Gri tonları
    color: "#424242",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
};
export default Signup;
