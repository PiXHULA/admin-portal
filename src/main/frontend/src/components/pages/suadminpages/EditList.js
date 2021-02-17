import React, {useEffect, useState} from "react";
import controller from "../../../helpers/Controller";
import {useHistory} from "react-router-dom";
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
                        <button style={editBtn} onClick={() => {
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
                    <button type="button" onClick={handleClick}>
                        Go back!
                    </button>
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
    'padding-top': '5vh',
    'align-items': 'center',
    'justify-content': 'center',
    'min-height': '1vh',
    'min-width': '80vh',
}

const ulStyle = {
    'display': 'flex-center',
    'padding-right':'5vh',
    'align-items': 'center',
    'justifyContent': 'center',
    'min-height': '1vh',
    'list-style-type': 'none',
}

const editBtn = {
    'width': '25%',
    'padding' : 0,
}

export default EditList;
