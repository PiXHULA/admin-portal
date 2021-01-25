import logo from './logo.svg';
import './App.css';
// import {useState, useCallback} from "react";
import axios from "axios";

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

const LoginButton = () => {
    const user = {
        username: "admin",
        password: "password"
    }
    return (
        <button onClick={() => {
            axios.post('https://admin-portal-examen.herokuapp.com/authenticate', user,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                }).then(response => {
                if (localStorage.getItem("jwt") === null) {
                    const token = response.data.jwt;
                    localStorage.setItem("jwt", token)
                    console.log("YOU HAVE LOGGED IN");
                    console.log(localStorage.getItem("jwt"));
                }
            }).catch(error => {
                console.log(error);
            })
        }}>
            Login
        </button>
    );
}

const HelloButton = () => {
    return (
        <button onClick={() => {
            if (localStorage.key(0) === 'jwt') {
                axios.get('https://admin-portal-examen.herokuapp.com/hello',
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                        },
                    }).then(response => {
                    console.log(response)
                }).catch(error => {
                    console.log(error);
                })
            } else{
                console.log("Please log in first!")
            }
        }}>
            HELLO
        </button>
    );
};

const LogOutButton = () => {
    return (
        <button onClick={() => {
                if (localStorage.length > 0) {
                    console.log("YOU HAVE LOGGED OUT");
                    localStorage.clear();
                }
            }}>
            Logout
        </button>
    );
};


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
