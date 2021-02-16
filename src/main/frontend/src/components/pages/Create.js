import React, {useState} from "react";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";

const Create = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() =>history.push("/dashboard"))
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
                    Name
                    <input type="text" placeholder="name"
                           onChange={event => setUser({...user, username: event.target.value})}/> <br/>
                    Password
                    <input type="text" placeholder="password"
                           onChange={event => setUser({...user, password: event.target.value})}/>
                </label>
                <button type="button" onClick={() => {
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
        </div>
    );
}

export default Create;
