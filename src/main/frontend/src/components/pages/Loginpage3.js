import React, {useState} from "react";
import axios from "axios";

const Loginpage3 = () => {
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");

    return (
            <form onSubmit={onSubmit}>
                <div>
                    <label>Username</label>
                    <input type="text" placeholder={"username"} onChange={(e) => setUser(e.target.value)}/>
                </div>
                <div>
                    <label>Password</label>
                    <input type="text" placeholder={"password"} onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <input type="submit" value="login"/>
            </form>
    )
}

const divBackground = {
    'backgroundColor': '#282c34',
    'minHeight': '100vh',
    'display': 'flex',
    'flexDirection': 'column',
    'alignItems': 'center',
    'justifyContent': 'center',
    'fontSize': 'calc(10px + 2vmin)',
    'color': 'white'
};


export default Loginpage3
