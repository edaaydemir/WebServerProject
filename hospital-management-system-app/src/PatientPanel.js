import React, { useState, useEffect } from "react";
import axios from "axios";
import queryString from "query-string"; // Import queryString library

function UpdatePatient() {
  const [patientData, setPatientData] = useState({
    name: "",
    surname: "",
    age: "",
    phoneNum: "",
    gender: "",
    email: "",
    city: "",
    status: "",
  });
  const [sessionKey, setSessionKey] = useState("");

  useEffect(() => {
    // Extract session key from the URL
    const parsed = queryString.parse(window.location.search);
    if (parsed.key) {
      setSessionKey(parsed.key);
    }
  }, []);

  useEffect(() => {
    if (sessionKey) {
      // Fetch patient data using the session key
      const fetchPatientData = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8080/getPatientByUuid?key=${sessionKey}`
          );
          setPatientData(response.data);
        } catch (error) {
          console.error("Error fetching patient data:", error);
        }
      };

      fetchPatientData();
    }
  }, [sessionKey]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPatientData({
      ...patientData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (!sessionKey) {
        console.error("Session key not available");
        return;
      }

      const response = await axios.put(
        `http://localhost:8080/updatePatient?key=${sessionKey}`,
        patientData
      );
      console.log("Patient data updated:", response.data);
      // Optionally, you can show a success message to the user
    } catch (error) {
      console.error("Error updating patient data:", error);
      // Optionally, you can show an error message to the user
    }
  };

  return (
    <div>
      <h2>Update Patient Information</h2>
      <form onSubmit={handleSubmit}>
        {/* Input fields for updating patient data */}
        <div>
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={patientData.patientname}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Surname:</label>
          <input
            type="text"
            name="surname"
            value={patientData.patientsurname}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Age:</label>
          <input
            type="text"
            name="age"
            value={patientData.age}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Phone Number:</label>
          <input
            type="text"
            name="phoneNum"
            value={patientData.phoneNum}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Gender:</label>
          <input
            type="text"
            name="gender"
            value={patientData.gender}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={patientData.email}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>City:</label>
          <input
            type="text"
            name="city"
            value={patientData.city}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Status:</label>
          <input
            type="text"
            name="status"
            value={patientData.status}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
}

export default UpdatePatient;
