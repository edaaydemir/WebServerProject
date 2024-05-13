import React, { useState } from "react";
import Navbar from "../navbar";
import Footer from '../footer';

function Login() {
  const [userData, setUserData] = useState({
    email: '',
    password: ''
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({
      ...userData,
      [name]: value
      
    });
    console.log(name , value);
  };

  return (
    <div>
      <Navbar />
      <div style={styles.container}>
        <div style={styles.form}>
          <h2 style={styles.title}>LOGIN</h2>
          <div style={styles.inputGroup}>
            <label htmlFor="email" style={styles.label}>E-Mail:</label>
            <input type="email" id="email" name="email" style={styles.input} onChange={handleChange} required />
          </div>
          <div style={styles.inputGroup}>
            <label htmlFor="password" style={styles.label}>Password:</label>
            <input type="password" id="password" name="password" style={styles.input} onChange={handleChange} required />
          </div>
          <div style={styles.signUp}>
            <button type="submit" style={styles.button}>Login</button>
          </div>
        </div>
      </div>
      <Footer></Footer>

    </div>

  );
}

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '60vh',
  },
  form: {
    width: '360px',
    padding: '20px',
    backgroundColor: '#f5f5f5', // Gri tonları
    borderRadius: '10px',
    boxShadow: '0px 0px 10px 0px rgba(0, 0, 0, 0.1)',
    textAlign: 'center',
  },
  title: {
    color: '#424242', // Koyu gri
    marginBottom: '20px',
  },

  inputGroup: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: '20px',
  },
  label: {
    color: '#424242', // Koyu gri
    marginRight: '10px',
  },
  input: {
    width: '150px',
    padding: '5px',
    border: '1px solid #424242', // Koyu gri
    borderRadius: '5px',
  },
  button: {
    padding: '10px 20px',
    backgroundColor: '#f5f5f5', // Gri tonları
    color: '#424242', // Koyu gri
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
};

export default Login;
