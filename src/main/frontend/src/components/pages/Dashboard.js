import React from 'react';
import {HelloButton} from "../buttons/HelloButton";
import {LogOutButton} from "../buttons/LogOutButton";

const Dashboard = () => {
    return (
        <>
        <h2>Dashboard</h2>
        <HelloButton btnText={"Show us hello"} endp={"/hello"}/>
        <HelloButton btnText={"Show us wow"} endp={"/wow"}/>
        <LogOutButton/>
        </>
    )
}


export default Dashboard;
