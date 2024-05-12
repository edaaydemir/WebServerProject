import React from 'react';
import { Link } from "react-router-dom";


function Footer() {
    return (
        <div style={{ borderStyle: "outset" }}>
            <h3 style={{ display: "flex", justifyContent: "center" }}>Contact</h3>

            <div style={{ display: "flex", justifyContent: "center" }}>

                <div style={{ alignItems: "center" }}>
                    <div>
                        esko
                    </div>
                    <div>
                        info@example.com
                    </div>
                    <div>abc
                    </div>
                    <div>xyz
                    </div>
                </div>
                <div style={{ display: "flex", justifyContent: "center" }}>
                    <div className='text-center p-4' style={{ backgroundColor: 'rgba(0, 0, 0, 0.05)' }}>
                        © 2021 Copyright:
                        <a className='text-reset fw-bold' href='https://mdbootstrap.com/'>
                            MDBootstrap.com
                        </a>
                    </div>
                </div>
            </div>


        </div>
    );
}
export default Footer;