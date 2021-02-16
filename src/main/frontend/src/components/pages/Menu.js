import controller from "../../helpers/Controller";
import React from "react";

import {useHistory} from "react-router-dom";

const Menu = () => {
    let history = useHistory();
    return (
        <span style={divDashboardPanel}>
            <ul style={listStyle}>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/curators"))}>curators</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/tags"))}>applications</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/cities"))}>tags</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/applications"))}>city</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/edit"))}>edit</button>
                </li>
                <li>
                    <button onClick={() => controller.setLoadingTrue(() => history.push("/superadmin"))}>superEdit</button>
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
