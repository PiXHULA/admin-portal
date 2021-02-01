import React, {useState, useContext, createContext} from 'react';
import {
    BrowserRouter as Router,
    Link,
    Route,
    Switch,
    useHistory,
    useLocation,
    Redirect, HashRouter
} from "react-router-dom";
import './css/App.css';


import WelcomePage from "./components/pages/WelcomePage";
import Dashboard from "./components/pages/Dashboard";
import Header from "./components/pages/Header";
import Footer from "./components/pages/Footer";
import Public from "./components/pages/Public";


const authContext = createContext();

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
    signin(cb) {
        fakeAuth.isAuthenticated = true;
        setTimeout(cb, 100); // fake async
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
            <HashRouter>
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
                    </Switch>
                        
                    </div>
                    </body>
                    <footer>
                        <Footer/>
                    </footer>
                </div>
            </HashRouter>
        </ProvideAuth>
    );
}

export default App;

const mainDiv = {
    'display': 'flex',
    'flexDirection': 'column',
    'alignItems': 'center',
    'justifyContent': 'center',
    'fontSize': 'calc(10px + 2vmin)',
    'color': 'white'
};





