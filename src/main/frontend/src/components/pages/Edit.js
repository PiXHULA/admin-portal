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

    const getUser = () => {
        axios.get(`api/v1/user/user/${localStorage.getItem("user")}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            setUser({id: response.data.id, name: response.data.username, password: response.data.password})
            console.log(response.data)
        }).catch(error => {
            console.log(error.response.data);
        })
    }
    useEffect(() => {
        getUser()
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

