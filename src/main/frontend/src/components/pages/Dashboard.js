import React, {useEffect, useState} from 'react';
import controller from "../../helpers/Controller";
import Nav from "./Nav";

const Dashboard = () => {

    const [userList, setUserList] = useState([]);


    useEffect(() => {
        controller.getUsers((response) => setUserList(response))
    }, []);

    const getUserList = () => {
        return (
            <ul style={listPanel}>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                    </li>
                ))}
            </ul>
        )
    }

    const getDashboardPanel = () => {
        return (
            <div style={divDashboardPanel}>
                <ul style={{'list-style-type': 'none'}}>
                    <li>
                        <button>curators</button>
                    </li>
                    <li>
                        <button>applications</button>
                    </li>
                    <li>
                        <button>tags</button>
                    </li>

                </ul>
            </div>
        )
    }
    return (<div>
        <h2>Dashboard</h2>
        {controller.isLoading() ?
        <h2>Loading...</h2> :
        <Nav/>}
        {controller.isLoading() ?
        <h2>Loading...</h2> :
        getDashboardPanel()}
        {controller.isLoading() ?
        <h2>Loading...</h2> :
        getUserList()}
    </div>);
};


const divDashboardPanel = {
    'display': 'flex',
    'align-items': 'left',
    'min-height': '1vh',
}

const listPanel = {
    'display': 'flex-center',
    'align-items': 'center',
    'min-height': '1vh',
}

export default Dashboard;
