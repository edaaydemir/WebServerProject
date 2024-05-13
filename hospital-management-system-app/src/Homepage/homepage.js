import React, { useState } from "react";
import Navbar from "../navbar";
import Footer from "../footer";


function Homepage() {
  return (
    <section>
      <div>
        <Navbar></Navbar>
        <img
          style={{ display: "block", width:"50%", margin: "20px auto" }}
          src={"/hospital.jpg"}
          alt="Hospital Image"
        />
        <Footer></Footer>
      </div>
    </section>
  );
}

export default Homepage;
