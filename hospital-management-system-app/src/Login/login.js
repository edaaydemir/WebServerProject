import React, { useEffect, useState } from "react";
import Navbar from "../navbar";
import Footer from "../footer";
import io from "socket.io-client";

const socket = io("http://localhost:3001");

function Login() {
  const [userData, setUserData] = useState({
    email: "",
    password: "",
  });
  const [serverData, setServerData] = useState({
    email: "",
    password: "",
  });

  const [loginError, setLoginError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({
      ...userData,
      [name]: value

    

    });
    console.log(name , value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Add logic for form submission or API requests here
  };

  useEffect(() => {
    // Burada eğer gerekirse, sunucudan gelen verilere abone olabilirsiniz
    socket.on("email", (hotmail) => {
      console.log(hotmail);
    });

    // Abonelikten çıkış sağlamak için
    socket.on("password", (şifre) => {
    console.log(şifre);
 });
  }, []);
 
  

  return (
    <div>
      <Navbar />
      <div style={styles.container}>
        <div style={styles.form}>
          <h2 style={styles.title}>LOGIN</h2>
          <form onSubmit={handleSubmit}>
            <div style={styles.inputGroup}>
              <label htmlFor="email" style={styles.label}>
                E-Mail:
              </label>
              <input
                type="email"
                id="email"
                name="email"
                style={styles.input}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.inputGroup}>
              <label htmlFor="password" style={styles.label}>
                Password:
              </label>
              <input
                type="password"
                id="password"
                name="password"
                style={styles.input}
                onChange={handleChange}
                required
              />
            </div>
            <div style={styles.error}>{loginError}</div>
            <div style={styles.signUp}>
              <button type="submit" style={styles.button}>
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
      <Footer />
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
