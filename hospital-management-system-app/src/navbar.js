import React from "react";
import { Link } from "react-router-dom";

const styles = {
  container: {
    backgroundColor: "#f5f5f5", // Gri tonları
    color: "#424242", // Koyu gri
    padding: "10px 20px",
    width: "100%",
    boxSizing: "border-box",
    boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
  },
  navbarLink: {
    textDecoration: "none",
    color: "#424242", // Koyu gri
    fontSize: "1.2rem",
    fontWeight: "bold",
  },
  button: {
    padding: "8px 16px",
    borderRadius: "5px",
    backgroundColor: "#fff",
    color: "#424242", // Mor tonu
    textDecoration: "none",
    border: "none",
    cursor: "pointer",
  },
};

function Navbar() {
  return (
    <div style={styles.container}>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <div>Hospital Management System </div>
        <Link to="/" style={styles.button}>
          Home
        </Link>
        <Link to="/aboutUs" style={styles.button}>
          About Us
        </Link>
        <div style={{ display: "flex", justifyContent: "end" }}>
          <Link to="/Doctorlogin" style={styles.button}>
            Doctor Login
          </Link>
          <Link to="/login" style={styles.button}>
            Patient Login
          </Link>
          <Link to="/signup" style={{ ...styles.button, marginLeft: "10px" }}>
            Sign Up
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Navbar;
