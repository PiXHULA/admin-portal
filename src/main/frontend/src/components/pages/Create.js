import React, {useState} from "react";
import auth from "../../helpers/Auth";
import {useHistory} from "react-router-dom";

const Create = () => {

    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }

    const [user, setUser] = useState({
        username: "",
        password: ""
    });

    return (
        <div>
            <h2>Create</h2>
            <form>
                <label>
                    Name:
                    <input type="text" placeholder="name"
                           onChange={event => setUser({...user, username: event.target.value})}/> <br/>
                    Password:
                    <input type="text" placeholder="name"
                           onChange={event => setUser({...user, password: event.target.value})}/>
                </label>
                <button type="button" onClick={() => {
                    auth.createUser(user, () => {
                        handleClick()
                    })
                }}>
                    Save
                </button>
            </form>
        </div>
    );
}

export default Create;
