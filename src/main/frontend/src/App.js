// import {useState, useCallback} from "react";

import {LoginButton} from "./components/buttons/LoginButton";
import {HelloButton} from "./components/buttons/HelloButton";
import {LogOutButton} from "./components/buttons/LogOutButton";

// const onClick = useCallback(()=> {
//
//     axios.post('http://localhost:8080/authenticate',
//         user, {
//             headers: {
//                 "Content-Type": "application/json"
//             }
//         }
//     ).then(() => {
//         console.log("WOW uploaded successfully");
//     }).catch(error => {
//         console.log(error);
//     });
//     },[]);

function App() {
       return (
            <div className="App" style={divBackground}>
                <header className="App-header">
                        <p>
                        Turistmo - Admin Portal
                        </p>

                        <LoginButton usern={"suAdmin"} password={"wuru"} btnText={"SUADMIN"}/>
                        <LoginButton usern={"admin"} password={"guru"} btnText={"ADMIN"}/>
                        <HelloButton btnText={"READ - WOW"} endp={"wow"}/>
                        <HelloButton btnText={"CREATE - HELLO"} endp={"hello"}/>
                        <LogOutButton/>
                </header>
            </div>
    );
}

export default App;

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


