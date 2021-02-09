import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import controller from "../../helpers/Controller";

const LandingPage = (props) => {
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");

    const validateForm = () => {
        return user.length > 0 && password.length > 0;
    }

    return (
        <div>
            <Form>
                <Form.Group size="lg">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        autoFocus
                        type="user"
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" disabled={!validateForm()} onClick={() => {
                    controller.login(user, password, () => {
                        if (controller.isAuthenticated()) {
                            props.history.push("/dashboard")
                        }
                    })
                }
                }>
                    Login
                </Button>
            </Form>
        </div>
    );

}

export default LandingPage;
