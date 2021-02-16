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
            <h3>Available users</h3>
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
                <h2>Create or Edit admin</h2>
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
    'padding-top': '5vh',
    'min-height': '1vh',
    'min-width': '80vh',
}

const listPanel = {
    'display': 'flex-center',
    'align-items': 'center',
    'min-height': '1vh',
    'list-style-type': 'none',
}

export default SUDashboard;
