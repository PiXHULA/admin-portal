import React from 'react';
import {
    HashRouter as Router,
    Route,
    Switch,
} from "react-router-dom";
import './css/App.css';
import LandingPage from "./components/pages/LandingPage";
import Header from "./components/pages/Header";
import Footer from "./components/pages/Footer";
import About from "./components/pages/About";
import {ProtectedRoute} from "./helpers/ProtectedRoute";
import {ErrorPage} from "./components/pages/ErrorPage";
import Dashboard from "./components/pages/Dashboard";
import Edit from "./components/pages/Edit";

function App() {

    return (
            <Router>
                <div>
                    <Header/>
                    <body>
                    <div style={mainDiv}>
                    <Switch>
                        <Route path="/" exact component={LandingPage}/>
                        <Route path="/about" component={About}/>
                        <ProtectedRoute path="/dashboard" component={Dashboard}/>
                        <ProtectedRoute path="/edit" component={Edit}/>
                        <Route path="*" component={ErrorPage}/>
                    </Switch>
                    </div>
                    </body>
                    <footer>
                        <Footer/>
                    </footer>
                </div>
            </Router>
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