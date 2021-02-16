import React, {useEffect, useState} from 'react';
import controller from "../../../helpers/Controller";
import Nav from "./Nav";
import {useHistory} from "react-router-dom";
import Menu from "../Menu";

const SUDashboard = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() => history.push("/dashboard"))
    }


    const [supUserList, setsupUserList] = useState([]);


    useEffect(() => {
        controller.getUsers((response) => setsupUserList(response))
    }, []);

    const getUserListAsSU = () => {
        return (
            <div>
            <ul style={listPanel}>
                {[...supUserList].map((user) => (
                    <li>
                        {user.username}
                    </li>
                ))}
            </ul>
            </div>
        )
    }

    return (
        <div style={divGround}>
            <div style={header}>
                <h2>SuperAdmin</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={divDashboardPanel}>
                <Nav/>
                    {controller.isLoading() ?
                        <h2>Loading...</h2> :
                        getUserListAsSU()}
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
    'min-height': '1vh',
    'min-width': '80vh',
    'background-color': 'blue',
}

const listPanel = {
    'display': 'flex-center',
    'align-items': 'center',
    'min-height': '1vh',
    'background-color': 'lightgray',
}

export default SUDashboard;
