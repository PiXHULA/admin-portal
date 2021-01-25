import logo from './logo.svg';
import './App.css';
// import {useState, useCallback} from "react";
import axios from "axios";
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
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                        <p>
                        Edit <code>src/App.js</code> and save to reload.
                        </p>
                        <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                        >
                        Learn React
                        </a>
                        <LoginButton/>
                        <HelloButton/>
                        <LogOutButton/>
                </header>
            </div>
    );
}

export default App;
