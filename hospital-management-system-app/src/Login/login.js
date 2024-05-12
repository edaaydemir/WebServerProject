import React, { useState } from "react";
import Navbar from "../navbar";


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
      };
      
  return (
    <div>
        <Navbar></Navbar>
        <div style={styles.container}>
         <div style={styles.formContainer}>
         <h2 style={styles.title}>LOGIN</h2>
          <div style={styles.inputGroup}>
            <label htmlFor="email" style={styles.label}>E-Mail:</label>
            <input type="email" id="email" name="email" style={styles.input} onChange={handleChange} required />
          </div>
          <div style={styles.inputGroup}>
            <label htmlFor="password" style={styles.label}>Password:</label>
            <input type="password" id="password" name="password" style={styles.input} onChange={handleChange} required />
          </div>
          </div>
          </div>
    </div>
  );

}
const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
    },
    formContainer: {
        width: '360px',
        padding: '20px',
        backgroundColor: '#f3e5f5',
        borderRadius: '10px',
        boxShadow: '0px 0px 10px 0px rgba(0, 0, 0, 0.1)',
        textAlign: 'center',
      },
      title: {
        color: '#6a1b9a',
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
      color: '#6a1b9a',
      marginRight: '10px',
    },
    input: {
      width: '150px',
      padding: '5px', 
      border: '1px solid #6a1b9a',
      borderRadius: '5px',
    },
   
      
  };

export default Login;