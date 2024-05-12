import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, useNavigate } from "react-router-dom";
import Login from "./Login/login";
import Homepage from "./Homepage/homepage";
import Aboutus from "./AboutUs/aboutus";
import SignUp from "./SignUp/signup";



function App() {

  return (
    <div style={styles.container}>
      <Router> {/* Router bileşeni ile App bileşenini sarmalayın */}
        <div className="App">
          <Routes>
            <Route exact path="/" element={<Homepage />} />
            <Route path="/homepage" element={<Homepage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/aboutus" element={<Aboutus />} />
            <Route path="/signup" element={<SignUp />} /> 
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