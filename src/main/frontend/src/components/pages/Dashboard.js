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
            <ul>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                    </li>
                ))}
            </ul>
        )
    }
    return (<div>
        <h2>Dashboard</h2>
        {controller.isLoading() ?
        <h2>Loading...</h2> :
        <Nav/>}
        {controller.isLoading() ?
        <h2>Loading...</h2> :
        getUserList()}
    </div>);
};

export default Dashboard;
