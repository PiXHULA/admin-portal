import {useHistory} from "react-router-dom";
import React from "react";

const City = () => {
    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }
    return (
        <div style={divGround}>
            <h2>Add or remove Cities</h2>

            <button type="button" onClick={handleClick}>
                Go back!
            </button>
        </div>


    )
}
const divGround = {
    'background-color': 'yellow',
    'min-width': '100vh',
}

export default City;

