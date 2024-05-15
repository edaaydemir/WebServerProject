import React, { useState, useEffect } from "react";
import axios from "axios";
import queryString from "query-string";

function AdminPanel() {
  const [doctorData, setDoctorData] = useState({
    doctorname: "",
    phoneNum: "",
    email: "",
    password: "",
    specialization: "",
    city: "",
    experience: "",
    type: "",
    availableTimeFrom: "",
    availableTimeTo: "",
  });

  const [sessionKey, setSessionKey] = useState("");

  useEffect(() => {
    // Extract session key from the URL
    const parsed = queryString.parse(window.location.search);
    if (parsed.key) {
      setSessionKey(parsed.key);
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDoctorData({
      ...doctorData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        `http://localhost:8080/registerDoctor?key=${sessionKey}`,
        doctorData
      );
      if (response.status === 201) {
        console.log("Doctor registration successful:", response.data);
        // You can add further logic here, e.g., show success message, reset form, etc.
      }
    } catch (error) {
      console.error("Error during doctor registration:", error);
      // Handle error, e.g., show error message to the user
    }
  };

  return (
    <div>
      <h2>Register Doctor</h2>
      <form onSubmit={handleSubmit}>
        <table>
          <tbody>
            <tr>
              <td>Doctor Name:</td>
              <td>
                <input
                  type="text"
                  name="doctorname"
                  value={doctorData.doctorname}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Phone Number:</td>
              <td>
                <input
                  type="text"
                  name="phoneNum"
                  value={doctorData.phoneNum}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Email:</td>
              <td>
                <input
                  type="email"
                  name="email"
                  value={doctorData.email}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Password:</td>
              <td>
                <input
                  type="password"
                  name="password"
                  value={doctorData.password}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Specialization:</td>
              <td>
                <input
                  type="text"
                  name="specialization"
                  value={doctorData.specialization}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>City:</td>
              <td>
                <input
                  type="text"
                  name="city"
                  value={doctorData.city}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Experience:</td>
              <td>
                <input
                  type="number"
                  name="experience"
                  value={doctorData.experience}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>

            <tr>
              <td>Available Time From:</td>
              <td>
                <input
                  type="text"
                  name="availableTimeFrom"
                  value={doctorData.availableTimeFrom}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Available Time To:</td>
              <td>
                <input
                  type="text"
                  name="availableTimeTo"
                  value={doctorData.availableTimeTo}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
          </tbody>
        </table>
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default AdminPanel;
