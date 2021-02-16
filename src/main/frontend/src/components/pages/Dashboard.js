import React, {useEffect, useState} from 'react';
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";
import Menu from "./Menu";

const Dashboard = () => {

    let history = useHistory();
    const [userList, setUserList] = useState([]);


    useEffect(() => {
        controller.getUsers((response) => setUserList(response))
    }, []);

    const getUserList = () => {
        return (
            <div style={divDashboardPanel}>
                <ul style={listPanel}>
                    {[...userList].map((user) => (
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
            <h2>Dashboard</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={divDashboardPanel}>
                {controller.isLoading() ?
                    <h2>Loading...</h2> :
                    getUserList()}
                </span>
            </div>
        </div>
    );
};

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
const listPanel = {
    'display': 'flex',
    'align-items': 'center',
    'min-height': '1vh',
    'background-color': 'lightgray',
}

export default Dashboard;
