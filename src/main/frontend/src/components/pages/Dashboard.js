import React, {useEffect, useState} from 'react';
import controller from "../../helpers/Controller";
import Menu from "./Menu";

const Dashboard = () => {

    const [user, setUser] = useState({
        id: "",
        name: "",
        password: ""
    });

    useEffect(() => {
        controller.getLoggedInUser((response) => setUser({
            id: response.id,
            name: response.username,
            password: response.password
        }))
    }, [])

    return (
        <div style={divGround}>
            <div style={header}>
            <h2>Dashboard</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={divDashboardPanel}>
                {controller.isLoading() ?
                    <h3>Loading...</h3> :
                    <h3>Welcome {user.name}</h3>}
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
    'align-items': 'left',
    'min-height': '1vh',
    'min-width': '80vh',
}


export default Dashboard;
