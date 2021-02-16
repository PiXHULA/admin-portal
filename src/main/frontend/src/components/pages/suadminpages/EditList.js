import React, {useEffect, useState} from "react";
import controller from "../../../helpers/Controller";
import {useHistory} from "react-router-dom";
import Nav from "./Nav";
import Menu from "../Menu";

const EditList = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() => history.push("/sudashboard"))
    }

    const editClick = () => {
        controller.setLoadingTrue(() => history.push("/edit"))
    }

    const [userList, setUserList] = useState([]);

    useEffect(() => {
        controller.getUsers((response) => {
            setUserList(response)
        })
    }, []);

    const getUserList = () => {
        return (
            <ul style={ulStyle}>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                        <button onClick={() => {
                            localStorage.setItem("user", user.id)
                            editClick()
                        }}>Edit
                        </button>
                    </li>
                ))}
            </ul>
        )
    }

    return (
        <div style={divGround}>
            <div style={header}>
                <h2>Select user to edit</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={spanDashboardPanel}>
                   {controller.isLoading() ?
                      <h2>Loading...</h2> :
                       getUserList()}
                    {controller.isLoading() ?
                    <h2>Loading...</h2> :
                    <button type="button" onClick={handleClick}>
                        Go back!
                    </button>}
              </span>
            </div>
        </div>
    );
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
const spanDashboardPanel = {
    'align-items': 'center',
    'justify-content': 'center',
    'min-height': '1vh',
    'min-width': '80vh',
}

const ulStyle = {
    'padding':'0',
    'display': 'flex',
    'flexDirection': 'row',
    'list-style-type': 'none',
    'justifyContent': 'space-around',
}


export default EditList;
