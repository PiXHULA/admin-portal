import React from 'react';

const Footer = () => {
    return (
                    <div style={footerDiv}>
                        <p>Copyright 2021 &#169;</p>
                    </div>
    )
}

const footerDiv = {
    'display': 'flex',
    'justify-content': 'center',
    'align-items': 'center',
   ' min-height': '1vh',
  }

export default Footer;