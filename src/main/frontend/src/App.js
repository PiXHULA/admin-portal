import React, {useState, useContext, createContext} from 'react';
import {
    HashRouter as Router,
    Route,
    Switch,
    useHistory,
    useLocation,
    Redirect,
} from "react-router-dom";
import './css/App.css';


import WelcomePage from "./components/pages/WelcomePage";
import Dashboard from "./components/pages/Dashboard";
import Header from "./components/pages/Header";
import Footer from "./components/pages/Footer";
import Public from "./components/pages/Public";
import axios from "axios";


const authContext = createContext();
//Context provides a way to pass data through the component tree without having to pass props down manually at every level.

function useAuth() {
    return useContext(authContext);
}


function ProvideAuth({children}) {
    const auth = useProvideAuth();
    return (
        <authContext.Provider value={auth}>
            {children}
        </authContext.Provider>
    );
}

function useProvideAuth() {
    const [user, setUser] = useState(null);

    const signin = callback => {
        return fakeAuth.signin(() => {
            setUser("user");
            callback();
        });
    };

    const signout = callback => {
        return fakeAuth.signout(() => {
            setUser(null);
            callback();
        });
    };

    return {
        user,
        signin,
        signout
    };
}
function LoginPage() {
    let history = useHistory();
    let location = useLocation();
    let auth = useAuth();

    let { from } = location.state || { from: { pathname: "/" } };
    let login = () => {
        auth.signin(() => {
            history.replace(from);
        });
    };

    return (
        <div>
            <h2>You must log in to view the page at {from.pathname}</h2>
            <button onClick={login}>Log in</button>
        </div>
    );
}

const fakeAuth = {
    isAuthenticated: false,
    async signin(cb) {
        await axios.post('http://localhost:8080/authenticate', {
                username: 'suAdmin',
                password: 'wuru'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            },
        ).then((response) => {
            console.log(response.data.jwt)
            const token = response.data.jwt;
            localStorage.setItem("jwt", token)
            fakeAuth.isAuthenticated = true;
            cb();
        }).catch(error => {
            console.log(error);
        });
    },
    signout(cb) {
        fakeAuth.isAuthenticated = false;
        setTimeout(cb, 100);
    }
};

function AuthButton() {
    let history = useHistory();
    let auth = useAuth();

    return auth.user ? (
        <p>
            Welcome!{" "}
            <button
                onClick={() => {
                    auth.signout(() => history.push("/"));
                }}
            >
                Sign out
            </button>
        </p>
    ) : (
        <p>Please log in</p>
    );
}


function PrivateRoute({children, ...rest}) {
    let auth = useAuth();
    return (
        <Route
            {...rest}
            render={({location}) =>
                auth.user ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: "/login",
                            state: {from: location}
                        }}
                    />
                )
            }
        />
    );
}

function App() {

    return (
        <ProvideAuth>
            <Router>
                <div>
                    <Header/>
                    <body>
                    <div style={mainDiv}>
                    <Switch>
                        <Route path="/" exact component={WelcomePage}/>
                        <Route path="/about" component={Public}/>
                        <Route path="/login" component={LoginPage}/>
                        <PrivateRoute path="/dashboard">
                            <Dashboard/>
                        </PrivateRoute>
                        <Route path="*" component={ErrorPage}/>
                    </Switch>

                    </div>
                    </body>
                    <footer>
                        <Footer/>
                    </footer>
                </div>
            </Router>
        </ProvideAuth>
    );
}

export default App;

const ErrorPage = () => {
    return (
    <h2>404 PAGE NOT FOUND</h2>
    )
}

const mainDiv = {
    'display': 'flex',
    'flexDirection': 'column',
    'alignItems': 'center',
    'justifyContent': 'center',
    'fontSize': 'calc(10px + 2vmin)',
    'color': 'white'
};





