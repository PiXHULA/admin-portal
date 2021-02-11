import React from "react";
import {useHistory} from "react-router-dom";

const About = () => {
    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }
    return (
        <div>
            <h2>About</h2>

            <button type="button" onClick={handleClick}>
                Go back!
            </button>
        </div>


    )
}

export default About;

