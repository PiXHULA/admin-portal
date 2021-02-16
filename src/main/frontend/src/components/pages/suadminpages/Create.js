import React, {useState} from "react";
import controller from "../../../helpers/Controller";
import {useHistory} from "react-router-dom";
import Menu from "../Menu";

const Create = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() =>history.push("/sudashboard"))
    }

    const validateForm = () => {
        return user.username.length > 0 && user.password.length > 0;
    }

    const [user, setUser] = useState({
        username: "",
        password: ""
    });

    return (
        <div style={divGround}>
            <div style={header}>
            <h2>Create a new admin account</h2>
            </div>
            <div style={divContent}>
                <Menu/>
                <span style={spanDashboardPanel}>
            <form>
                <label>
                    Name
                    <input type="text" placeholder="name"
                           onChange={event => setUser({...user, username: event.target.value})}/> <br/>
                    Password
                    <input type="password" placeholder="password"
                           onChange={event => setUser({...user, password: event.target.value})}/>
                </label>
                <button
                    type="button"
                    disabled={!validateForm()}
                    onClick={() => {
                    controller.createUser(user, () => {
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
    'padding-top': '5vh',
    'align-items': 'center',
    'justify-content': 'center',
    'min-height': '1vh',
    'min-width': '80vh',
}


export default Create;
