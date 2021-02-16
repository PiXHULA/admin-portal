import React, {useEffect, useState} from 'react';
import controller from "../../../helpers/Controller";
import Nav from "./Nav";
import {useHistory} from "react-router-dom";

const SUDashboard = () => {

    let history = useHistory();
    const [supUserList, setsupUserList] = useState([]);


    useEffect(() => {
        controller.getUsers((response) => setsupUserList(response))
    }, []);

    const getUserListAsSU = () => {
        return (
            <ul style={listPanel}>
                {[...supUserList].map((user) => (
                    <li>
                        {user.username}
                    </li>
                ))}
            </ul>
        )
    }

    const getMenu = () => {
        return (
            <div style={divDashboardPanel}>
                <ul style={{'list-style-type': 'none'}}>
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
                        <button onClick={() => controller.setLoadingTrue(() => history.push("/superadmin"))}>superEdit</button>
                    </li>
                </ul>

            </div>
        )
    }
    return (
        <div style={divGround}>
            <h2>Dashboard</h2>
            <Nav/>
            {controller.isLoading() ?
                <h2>Loading...</h2> :
                getMenu()}
            {controller.isLoading() ?
                <h2>Loading...</h2> :
                getUserListAsSU()}
        </div>
    );
};


const divDashboardPanel = {
    'display': 'flex',
    'align-items': 'left',
    'min-height': '1vh',
    'background-color': 'red',
}

const divGround = {
    'background-color': 'yellow',
}


const listPanel = {
    'display': 'flex-center',
    'align-items': 'center',
    'min-height': '1vh',
    'background-color': 'lightgray',
}

export default SUDashboard;
