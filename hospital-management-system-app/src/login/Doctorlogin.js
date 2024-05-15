import React, { useEffect, useState } from "react";
import Navbar from "../navbar";
import Footer from "../footer";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Doctorlogin() {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({
    email: "",
    password: "",
  });
  const [loginError, setLoginError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({
      ...userData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/loginDoctor",
        userData
      );
      if (response.status === 200) {
        const { uuid } = response.data; // Assuming the backend returns UUID
        console.log("Login successful! UUID:", uuid);
        // Redirect to another page using the retrieved UUID
        const urlWithSessionId = `/DoctorPanel?key=${uuid}`;
        navigate(urlWithSessionId);
      } else {
        const errorData = response.data;
        setLoginError(errorData.message);
      }
    } catch (error) {
      console.error("Error during login:", error);
    }
  };

  return (
    <div>
      <Navbar />
      <div style={styles.container}>
        <div style={styles.form}>
          <h2 style={styles.title}>DOCTOR LOGIN</h2>
          <form onSubmit={handleSubmit}>
            <div style={styles.inputGroup}>
              <label style={styles.label}>Email:</label>
              <input
                type="email"
                name="email"
                value={userData.email}
                style={styles.input}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label style={styles.label}>Password:</label>
              <input
                type="password"
                name="password"
                value={userData.password}
                style={styles.input}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.error}>{loginError}</div>
            <div style={styles.signUp}></div>
            <button type="submit" style={styles.button}>
              Login
            </button>
            {loginError && <p style={{ color: "red" }}>{loginError}</p>}
          </form>
        </div>
      </div>
      <Footer />
    </div>
  );
}

const styles = {
  container: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    height: "60vh",
  },
  form: {
    width: "360px",
    padding: "20px",
    backgroundColor: "#f5f5f5", // Gri tonları
    borderRadius: "10px",
    boxShadow: "0px 0px 10px 0px rgba(0, 0, 0, 0.1)",
    textAlign: "center",
  },
  title: {
    color: "#424242", // Koyu gri
    marginBottom: "20px",
  },
  inputGroup: {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    marginBottom: "20px",
  },
  label: {
    color: "#424242", // Koyu gri
    marginRight: "10px",
  },
  input: {
    width: "150px",
    padding: "5px",
    border: "1px solid #424242", // Koyu gri
    borderRadius: "5px",
  },
  button: {
    padding: "10px 20px",
    backgroundColor: "#f5f5f5", // Gri tonları
    color: "#424242", // Koyu gri
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
};

export default Doctorlogin;
