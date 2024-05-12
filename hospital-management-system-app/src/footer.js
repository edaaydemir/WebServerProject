import React from "react";
import { Link } from "react-router-dom";

const styles = {
  footer: {
    position: "",
    bottom: 0,
    left: 0,
    width: "100%",
    backgroundColor: "#f8f9fa", // Footer rengi
    borderTop: "1px solid #dee2e6" // Footer'ın üst kısmına ince bir çizgi
  }
};

function Footer() {
  return (
    <div style={styles.footer}>
      <div style={{ borderStyle: "ridge" }}>
        <div style={{ display: "flex", justifyContent: "space-evenly" }}>
        <div style={{ alignItems: "center" }}>
            <h3>Working Days</h3>
            <div>Monday</div>
            <div>Tuesday</div>
            <div>Wednesday</div>
            <div>Thursday</div>
            <div>Friday</div>
          </div>
          <div style={{ alignItems: "center" }}>
            <h3>Contact</h3>
            <div style={{ display: "flex", justifyContent: "center" }}>
              <div style={{ alignItems: "center" }}>
                <div>esko</div>
                <div>info@example.com</div>
                <div>abc</div>
                <div>xyz</div>
              </div>
            </div>
          </div>

          
        </div>
      </div>
    </div>
  );
}

export default Footer;