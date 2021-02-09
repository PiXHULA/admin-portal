import React, {useEffect, useState} from 'react';
import controller from "../../helpers/Controller";

const Dashboard = (props) => {

    const [userList, setUserList] = useState([]);
    const [currentUser, setCurrentUser] = useState({
        name: "",
        password: "",
        role: ""
    });

    useEffect(() => {
        controller.getUsers((response) => setUserList(response))
        controller.getCurrentUser(response => setCurrentUser({
            name: response.username,
            password: response.password,
            role: response.roles[0].roleName
        }))
    }, [...userList]);

    const getUserList = () => {
        return (
            <ul>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                        <button onClick={() => {
                            localStorage.setItem("user", user.id)
                            props.history.push("/edit")
                        }}>Edit
                        </button>
                    </li>
                ))}
            </ul>
        )
    }

    const getCreateButton = (currentUser) => {
        return (
            currentUser.role === "SUPERADMIN" &&
            <button onClick={() => {
                props.history.push("/create")
            }}>
                Create a new admin
            </button>
        )
    }

    const getDeleteButton = (currentUser) => {
        return (
            currentUser.role === "SUPERADMIN" &&
            <button onClick={() => {
                props.history.push("/delete")
            }}>
                Delete an admin
            </button>
        )
    }

    return (<div>
        <h2>Dashboard</h2>

        {getUserList()}
        {getCreateButton(currentUser)}
        {getDeleteButton(currentUser)}
        <button onClick={() => {
            if (localStorage.length > 0) {
                controller.logout(() => {
                    console.log("YOU HAVE LOGGED OUT");
                    localStorage.clear();
                    props.history.push("/")
                })
            }
        }}>
            Logout
        </button>
    </div>);
};

export default Dashboard;
