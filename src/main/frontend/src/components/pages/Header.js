import React from "react";
import {Link, Redirect} from "react-router-dom";
import auth from "../../helpers/Auth"

const Header = () => {

    // useEffect(()=> {
    //     auth.isAuthenticated()
    // },[auth.authenticated])

    return (
        <div style={headerStyle}>
            <h2>Turistmo - Admin Portal</h2>
            <ul style={ulStyle}>
                <li style={liStyle}>
                    <Link to="/">Home</Link>
                </li>
                <li style={liStyle}>
                    <Link to="/dashboard">Dashboard</Link>
                </li>
                <li style={liStyle}>
                    <Link to="/about">About</Link>
                </li>
                <li style={liStyle}>
                    {auth.isAuthenticated()
                        ? <button onClick={()=> auth.logout(()=> <Redirect to={"/"}/>)}>logout</button>
                        : null }
                </li>
            </ul>
        </div>
    )
}

const ulStyle = {
    'display': 'flex',
    'flexDirection': 'row',
    'list-style-type': 'none',
    'justifyContent': 'space-around',
}

const liStyle = {
    'padding-left': '5vh',
}


const headerStyle = {
    'display': 'flex',
    'flexDirection': 'row',
    'alignItems': 'center',
    'justifyContent': 'space-around',
    'min-height': '1vh',
    'fontSize': 'calc(10px + 2vmin)',
}

export default Header;
