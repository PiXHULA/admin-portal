import {useHistory} from "react-router-dom";
import React from "react";
import Menu from "../Menu";

const Tags = () => {
    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }
    return (
        <div style={divGround}>
            <div style={header}>
            <h2>Add or remove Tags</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={divDashboardPanel}>
                <button type="button" onClick={handleClick}>
                    Go back!
                </button>
                </span>
            </div>
        </div>
    )
}

const divContent = {
    'display': 'flex',
    'flexDirection': 'row',
}
const header = {
    'display': 'flex',
    'align-items': 'left',
}

const divGround = {
    'min-width': '100vh',
    'min-height': '80vh',
}
const divDashboardPanel = {
    'display': 'flex',
    'align-items': 'center',
    'min-height': '1vh',
    'min-width': '80vh',
    'background-color': 'blue',
}

export default Tags;

