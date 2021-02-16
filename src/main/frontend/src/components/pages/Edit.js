import React, {useEffect, useState} from "react";
import axios from "axios";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";
import Menu from "./Menu";

const Edit = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() => history.push("/dashboard"))
    }

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
                <h2>Reset Password for {user.name}</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={spanDashboardPanel}>
                        <form>
                            <label>
                                Change password:
                                <input type="password" onChange={e => setUser({...user, password: e.target.value})}/>
                            </label>
                            <button type="button" onClick={() => {
                                controller.editUser(user, () => {
                                    handleClick()
                                })
                            }}>
                                Save
                            </button>
                        </form>
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
    'align-items': 'center',
    'justify-content': 'center',
    'min-height': '1vh',
    'min-width': '80vh',
}

export default Edit;

