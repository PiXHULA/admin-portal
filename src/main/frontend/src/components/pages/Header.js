import React from "react";
import {Link} from "react-router-dom";

const Header = () => {
    return (
        <div>
            <h2>Turistmo - Admin Portal</h2>
            <ul>
                <li>
                    <Link to="/public">Public Page</Link>
                </li>
                <li>
                    <Link to="/protected">Protected Page</Link>
                </li>
            </ul>
        </div>
    )
}
export default Header;
