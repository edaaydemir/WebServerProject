import React, { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  useNavigate,
} from "react-router-dom";
import Login from "./login/login";
import Homepage from "./homepage/homepage";
import Aboutus from "./aboutus/aboutus";
import SignUp from "./signup/signup";
import Doctorlogin from "./login/Doctorlogin";
import AdminPanel from "./AdminPanel";
import PatientPanel from "./PatientPanel";
import DoctorPanel from "./DoctorPanel";

function App() {
  return (
    <div style={styles.container}>
      <Router>
        {" "}
        {/* Router bileşeni ile App bileşenini sarmalayın */}
        <div className="App">
          <Routes>
            <Route exact path="/" element={<Homepage />} />
            <Route path="/homepage" element={<Homepage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/aboutus" element={<Aboutus />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/Doctorlogin" element={<Doctorlogin />} />
            <Route path="/AdminPanel" element={<AdminPanel />} />
            <Route path="/PatientPanel" element={<PatientPanel />} />
            <Route path="/DoctorPanel" element={<DoctorPanel />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

const styles = {
  container: {
    width: "100%",
  },
};

export default App;
