import React, {useState} from "react";
import App from '../../css/App.css';
import controller from "../../helpers/Controller";

const LandingPage = (props) => {
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");

    const validateForm = () => {
        return user.length > 0 && password.length > 0;
    }

    return (
        <div>
            <div style={App.divForms}>
                <form onSubmit={(event) => {
                    event.preventDefault();
                    controller.login(user, password, () => {
                        if (controller.isAuthenticated()) {
                            props.history.push("/dashboard")
                        }
                    })
                }}>
                    <label>Username</label>
                    <input
                        autoFocus
                        type="text"
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                    />
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <input
                        type='submit'
                        disabled={!validateForm()}>
                    </input>
                </form>
            </div>
        </div>
    );

}

export default LandingPage;
