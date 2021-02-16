import React, {useEffect, useState} from "react";
import axios from "axios";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";

const Edit = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() =>history.push("/dashboard"))
    }

    const [user, setUser] = useState({
        id: "",
        name: "",
        password: ""
    });

    useEffect(() => {
        controller.getUser((response) =>  setUser({id: response.id, name: response.username, password: response.password}))
    }, [])

    return (
        <div>
            <h2>Reset Password for {user.name}</h2>
            <form>
                <label>
                    Change password:
                    <input type="password" onChange={e => setUser({...user,password: e.target.value})}/>
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
        </div>
    );
}

export default Edit;

