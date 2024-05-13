import React, { useState } from 'react';
import Navbar from '../navbar';
import Footer from '../footer';

function Signup() {

  const [userData, setUserData] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    surname: '',
    phoneNumber: '+90',
    birthday: ''
  });

  const [phoneNumberError, setPhoneNumberError] = useState(false);
  const [error, setError] = useState('');
  

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'phoneNumber') {
      if (value.startsWith('+90')) {
        setUserData({
          ...userData,
          [name]: value
        });
      } else if (value === '+') {
        setUserData({
          ...userData,
          [name]: value
        });
      } else if (value.match(/^\+90\d{0,10}$/)) {
        setUserData({
          ...userData,
          [name]: value
        });
      }
    } else {
      setUserData({
        ...userData,
        [name]: value
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

  };

  const validatePhoneNumber = (phoneNumber) => {
    const regex = /^\+90\d{10}$/;
    return regex.test(phoneNumber);
  };

  return (
    <div> <Navbar></Navbar>
      <div style={styles.container}>
        <div style={styles.form}>
          <h2 style={styles.title}>SIGN UP</h2>
          <form onSubmit={handleSubmit}>
            <div style={styles.inputGroup}>
              <label htmlFor="email" style={styles.label}>E-Mail:</label>
              <input type="email" id="email" name="email" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="password" style={styles.label}>Password:</label>
              <input type="password" id="password" name="password" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="confirmPassword" style={styles.label}>Confirm Password:</label>
              <input type="password" id="confirmPassword" name="confirmPassword" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="name" style={styles.label}>Name:</label>
              <input type="text" id="name" name="name" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="surname" style={styles.label}>Surname:</label>
              <input type="text" id="surname" name="surname" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="phoneNumber" style={styles.label}>Phone Number:</label>
              <input type="text" id="phoneNumber" name="phoneNumber" style={styles.input} onChange={handleChange} value={userData.phoneNumber} required />
              {phoneNumberError && <span style={styles.error}>Please enter a valid phone number starting with +90 and containing exactly 10 digits.</span>}
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="birthday" style={styles.label}>Birthday:</label>
              <input type="date" id="birthday" name="birthday" style={styles.input} onChange={handleChange} required />
            </div>
            <div style={styles.signUp}>
              <button type="submit" style={styles.button}>Sign Up</button>
            </div>
          </form>
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

    height: '90vh',
    marginTop: '30px',
    marginBottom: '30px'

  },
  form: {
    width: '400px',
    padding: '20px',
    backgroundColor: '#f5f5f5',
    borderRadius: '10px',
    boxShadow: '0px 0px 10px 0px rgba(0, 0, 0, 0.1)',
    textAlign: 'center',
  },
  title: {
    color: '#424242',
    marginBottom: '10px',
  },
  inputGroup: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: '5px',
  },
  label: {
    color: '#424242',
    marginRight: '10px',
    minWidth: '120px',
    textAlign: 'left',
  },
  input: {
    flex: '1',
    padding: '10px',
    border: '1px solid #424242',
    borderRadius: '5px',
    maxWidth: '200px',
  },
  signUp: {
    marginBottom: '20px',
  },
  button: {
    padding: '10px 20px',
    backgroundColor: '#f5f5f5', // Gri tonları
    color: '#424242',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
};

export default Signup;