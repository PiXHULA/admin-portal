import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import controller from "../../helpers/Controller";


const Nav = () => {
    let history = useHistory();

    const landingClick = () => {
        history.push("/");
    }

    const deleteClick = () => {
        history.push("/delete");
    }

    const createClick = () => {
        history.push("/create");
    }

    const showEditClick = () => {
        history.push("/ShowEdit");
    }

    const editClick = () => {
            localStorage.setItem("user", currentUser.id)
        history.push("/edit");
    }

    const [currentUser, setCurrentUser] = useState({
        name: "",
        password: "",
        role: ""
    });

    useEffect(() => {
        controller.getCurrentUser(response => setCurrentUser({
            id: response.id,
            name: response.username,
            password: response.password,
            role: response.roles[0].roleName
        }))
    }, []);

    const getDeleteButton = (currentUser) => {
        return (
            currentUser.role === "SUPERADMIN" &&
            <button onClick={deleteClick}>
                Delete an admin
            </button>
        )
    }

    const getCreateButton = (currentUser) => {
        return (
            currentUser.role === "SUPERADMIN" &&
            <button onClick={createClick}>
                Create a new admin
            </button>
        )
    }

    const getEditAllButton = (currentUser) => {
        return (
            currentUser.role === "SUPERADMIN" &&
            <button onClick={showEditClick}>
                Edit an admin
            </button>
        )
    }
    const getEditButton = (currentUser) => {
        return (
            currentUser.role === "ADMIN" &&
            <button onClick={editClick}>
                Edit
            </button>
        )
    }

    return (
        <div style={headerStyle}>
            <ul style={ulStyle}>
                <li style={liStyle}>{getCreateButton(currentUser)}</li>
                <li style={liStyle}>{getEditAllButton(currentUser)}</li>
                <li style={liStyle}>{getEditButton(currentUser)}</li>
                <li style={liStyle}>{getDeleteButton(currentUser)}</li>
                <li style={liStyle}>
                    <button onClick={() => {
                        if (localStorage.length > 0) {
                            controller.logout(() => {
                                console.log("YOU HAVE LOGGED OUT");
                                localStorage.clear();
                                landingClick()
                            })
                        }
                    }}>
                        Logout
                    </button>
                </li>
            </ul>
        </div>
    )
}

const ulStyle = {
    'display': 'flex',
    'flexDirection': 'row',
    'list-style-type': 'none',
    'justifyContent': 'space-around',
}

const liStyle = {
    'padding-left': '5vh',
}


const headerStyle = {
    'display': 'flex',
    'flexDirection': 'row',
    'alignItems': 'center',
    'justifyContent': 'space-around',
    'min-height': '1vh',
    'fontSize': 'calc(10px + 2vmin)',
}

export default Nav;
