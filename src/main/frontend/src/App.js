import React from 'react';
import {
    Route,
    Switch,
} from "react-router-dom";
import './css/App.css';
import LandingPage from "./components/pages/LandingPage";
import Footer from "./components/pages/Footer";
import {ProtectedRoute} from "./helpers/ProtectedRoute";
import {ErrorPage} from "./components/pages/ErrorPage";
import Dashboard from "./components/pages/Dashboard";
import Edit from "./components/pages/Edit";
import Create from "./components/pages/suadminpages/Create";
import Delete from "./components/pages/suadminpages/Delete";
import Header from "./components/pages/Header";
import EditList from "./components/pages/suadminpages/EditList";
import Curators from "./components/pages/turistmopages/Curators";
import Tags from "./components/pages/turistmopages/Tags";
import City from "./components/pages/turistmopages/City";
import Applications from "./components/pages/turistmopages/Applications";
import SUDashboard from "./components/pages/suadminpages/SUDashboard";

function App() {
    return (
        <div>
            <Header/>
            <body>
            <div style={mainDiv}>
                <Switch>
                    <Route path="/" exact component={LandingPage}/>
                    <ProtectedRoute path="/dashboard" component={Dashboard}/>
                    <ProtectedRoute path="/curators" component={Curators}/>
                    <ProtectedRoute path="/tags" component={Tags}/>
                    <ProtectedRoute path="/cities" component={City}/>
                    <ProtectedRoute path="/applications" component={Applications}/>
                    <ProtectedRoute path="/edit" component={Edit}/>
                    <ProtectedRoute path="/sudashboard" component={SUDashboard}/>
                    <ProtectedRoute path="/editall" component={EditList} />
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
