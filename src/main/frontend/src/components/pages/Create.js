import React, {useState} from "react";
import auth from "../../helpers/Auth";

const Create = (props) => {

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
                    <input type="text" placeholder="name" onChange={event => setUser({...user,username:event.target.value})}/> <br/>
                    Password:
                    <input type="text" placeholder="name" onChange={event => setUser({...user,password:event.target.value})}/>
                </label>
                <button  onClick={() => {
                   auth.createUser(user, () => {
                       if (auth.isAuthenticated()) {
                           props.history.push("?#/dashboard")
                       }
                    })}}>
                    Save
                </button>
            </form>
        </div>
    );
}

export default Create;
