import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import controller from "../../../helpers/Controller";


const Nav = () => {
    let history = useHistory();

    const landingClick = () => {
        controller.setLoadingTrue(() => history.push("/"));
    }

    const deleteClick = () => {
        controller.setLoadingTrue(() => history.push("/superadmin/delete"));
    }

    const createClick = () => {
        // controller.setLoadingTrue(() => history.push("/superadmin/create"));
        history.push("/superadmin/create");
    }

    const showEditClick = () => {
        controller.setLoadingTrue(() => history.push("/superadmin/editlist"));
    }

    const editClick = () => {
        localStorage.setItem("user", currentUser.id)
        controller.setLoadingTrue(() => history.push("/superadmin/edit"));
    }

    const logoutClick = () => {
        controller.logout(() => {
            console.log("YOU HAVE LOGGED OUT");
            localStorage.clear();
            landingClick()
        })
    }


    const [currentUser, setCurrentUser] = useState({
        name: "",
        password: "",
        role: ""
    });

    useEffect(() => {
        controller.getLoggedInUser(response => setCurrentUser({
            id: response.id,
            name: response.username,
            password: response.password,
            role: response.roles[0].roleName
        }))
    }, []);

    const getDeleteButton = () => {
        return (
            <button onClick={deleteClick}>
                Delete an admin
            </button>
        )
    }

    const getCreateButton = () => {
        return (
            <button onClick={createClick}>
                Create a new admin
            </button>
        )
    }

    const getEditAllButton = () => {
        return (
            <button onClick={showEditClick}>
                Edit an admin
            </button>
        )
    }

    const getLogoutButton = () => {
        return (
            <button onClick={logoutClick}>
                Logout
            </button>
        )
    }

    return (
        <div style={headerStyle}>
            <ul style={ulStyle}>
                <li style={liStyle}>{getCreateButton()}</li>
                <li style={liStyle}>{getEditAllButton()}</li>
                <li style={liStyle}>{getDeleteButton()}</li>
                <li style={liStyle}>{getLogoutButton()}</li>
            </ul>
        </div>
    )
}

const ulStyle = {
    'padding':'0',
    'display': 'flex',
    'flexDirection': 'row',
    'list-style-type': 'none',
    'justifyContent': 'space-around',
}

const liStyle = {
    // 'padding': '5vh',
}

const headerStyle = {
    // // 'display': 'flex',
    // 'flexDirection': 'row',
    // 'alignItems': 'center',
    // 'justifyContent': 'space-around',
    'min-height': '1vh',
    'fontSize': 'calc(10px + 2vmin)',
}

export default Nav;
