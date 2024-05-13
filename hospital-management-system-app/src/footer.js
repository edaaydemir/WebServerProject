import React from "react";
import { Link } from "react-router-dom";

const styles = {
  footer: {
    backgroundColor: "#f8f9fa",      
    width: "100%",
    height: "100px",
    position: "fixed",
    bottom: 0,
    left: 0,
    
  },
};

function Footer() {
  return (
    <div style={styles.footer}>
      <div>
        <div style={{ display: "flex", justifyContent: "space-evenly" }}>
          <div style={{ alignItems: "center" }}>
            <h3>Working Days</h3>
            <div>Monday Tuesday Wednesday Thursday Friday </div>           
            
          </div>
          <div style={{ alignItems: "center" }}>
            <h3>Contact</h3>
            <div style={{ display: "flex", justifyContent: "center" }}>
              <div style={{ alignItems: "center" }}>               
                <div>info@example.com</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Footer;
