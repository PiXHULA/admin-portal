import React from 'react';
import {EndpointButton} from "../buttons/EndpointButton";
import auth from "../../helpers/Auth";

const Dashboard = (props) => {
    return (
        <>
        <h2>Dashboard</h2>
        <EndpointButton btnText={"Show us hello"} endp={"/hello"}/>
        <EndpointButton btnText={"Show us wow"} endp={"/wow"}/>
        <button onClick={() => {
            if (localStorage.length > 0) {
                auth.logout(()=>{
                    console.log("YOU HAVE LOGGED OUT");
                    localStorage.clear();
                    props.history.push("/")
                })
            }
        }}>
            Logout
        </button>
        </>
    )
}


export default Dashboard;
