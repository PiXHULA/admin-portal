import React, {useEffect, useState} from "react";
import axios from "axios";
import auth from "../../helpers/Auth";

const Edit = (props) => {

    const [user, setUser] = useState({
        id: "",
        name: "",
        password: ""
    });

    const getUser = () => {
        axios.get(`api/v1/user/user`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            setUser({id: response.data.id, name: response.data.username, password: response.data.password})
            console.log(response.data)
        }).catch(error => {
            console.log(error);
        })
    }
    useEffect(() => {
        getUser()
    }, [])

    const setPassword = (password) => {
        setUser({id: user.id, name: user.username, password: password})
    }

    return (
        <div>
            <h2>Edit</h2>
            <form>
                <label>
                    Name:
                    <input type="text" disabled={true} placeholder={user.name}/> <br/>
                    Change password:
                    <input type="text" onChange={e => setPassword(e.target.value)}/>
                </label>
                <button  onClick={() => {
                    auth.editUser(user, () => {
                    props.history.push("/dashboard")
                })}}>
                    Save
                    </button>
            </form>
        </div>
    );
}

export default Edit;

