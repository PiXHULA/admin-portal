import controller from "../../helpers/Controller";
import React, {useEffect, useState} from "react";

import {useHistory} from "react-router-dom";

const Menu = () => {
    let history = useHistory();

    const [currentUser, setCurrentUser] = useState({
        name: "",
        password: "",
        role: ""
    });

    useEffect(() => {
        controller.getLoggedInUser(response => setCurrentUser({
            id: response.id,
            name: response.username,
            password: response.password,
            role: response.roles[0].roleName
        }))
    }, []);

    return (
        <span style={divDashboardPanel}>
            <ul style={listStyle}>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/curators"))}>curators</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/applications"))}>applications</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/tags"))}>tags</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/cities"))}>city</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/edit"))}>edit</button>
                </li>
                <li>
                    {currentUser.role === "SUPERADMIN" ?
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/superadmin"))}>
                        more
                    </button>:
                    <button disabled={true} onClick={() => controller.setLoadingTrue(() => history.push("/superadmin"))}>
                        more
                    </button> }
                </li>
            </ul>
        </span>
    )
}
const divDashboardPanel = {
    'display': 'flex',
    'align-items': 'center',
    'min-height': '1vh',
    'min-width': '20vh',
    'background-color': 'red',
}

const listStyle = {
    'padding' : '0',
    'list-style-type': 'none',
}

export default Menu;
