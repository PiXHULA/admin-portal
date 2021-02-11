import React from 'react';
import {
    Route,
    Switch,
} from "react-router-dom";
import './css/App.css';
import LandingPage from "./components/pages/LandingPage";
import Footer from "./components/pages/Footer";
import About from "./components/pages/About";
import {ProtectedRoute} from "./helpers/ProtectedRoute";
import {ErrorPage} from "./components/pages/ErrorPage";
import Dashboard from "./components/pages/Dashboard";
import Edit from "./components/pages/Edit";
import Create from "./components/pages/Create";
import Delete from "./components/pages/Delete";
import Header from "./components/pages/Header";
import ShowEdit from "./components/pages/ShowEdit";

function App() {
    return (
        <div>
            <Header/>
            <body>
            <div style={mainDiv}>
                <Switch>
                    <Route path="/" exact component={LandingPage}/>
                    <Route path="/about" component={About}/>
                    <ProtectedRoute path="/dashboard" component={Dashboard}/>
                    <ProtectedRoute path="/ShowEdit" component={ShowEdit}/>
                    <ProtectedRoute path="/edit" component={Edit}/>
                    <ProtectedRoute path="/create" component={Create}/>
                    <ProtectedRoute path="/delete" component={Delete}/>
                    <Route path="*" component={ErrorPage}/>
                </Switch>
            </div>
            </body>
            <footer>
                <Footer/>
            </footer>
        </div>
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
