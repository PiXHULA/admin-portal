// import {BrowserRouter as Router, Link} from "react-router-dom";
// import React, {useState} from "react";
// import axios from "axios";
//
// const LoginPage2 = (callback) => {
//     // const [user, setUser] = useState("");
//     // const [password, setPassword] = useState("");
//     //
//     // const onSubmit = async (e) => {
//     //     e.preventDefault()
//     //     if (!user) {
//     //         console.log("Please add user");
//     //     }
//     //     if (!password) {
//     //         console.log("Please add password");
//     //     }
//     //
//     //     await axios.post('http://localhost:8080/authenticate', {
//     //             username: user,
//     //             password: password
//     //         }, {
//     //             headers: {
//     //                 "Content-Type": "application/json"
//     //             }
//     //         },
//     //     ).then((e) => {
//     //         // console.log("WOW uploaded successfully");
//     //         console.log(e.data.jwt)
//     //         callback();
//     //     }).catch(error => {
//     //         console.log(error);
//     //     });
//     //
//     // }
//     // return (
//     //     <div className="App" style={divBackground}>
//     //         <header className="App-header">
//     //             <p>Turistmo - Admin Portal</p>
//     //         </header>
//     //         <body>
//     //         <form onSubmit={onSubmit}>
//     //             <div>
//     //                 <label>Username</label>
//     //                 <input type="text" placeholder={"username"} onChange={(e) => setUser(e.target.value)}/>
//     //             </div>
//     //             <div>
//     //                 <label>Password</label>
//     //                 <input type="text" placeholder={"password"} onChange={(e) => setPassword(e.target.value)}/>
//     //             </div>
//     //             <input type="submit" value="login"/>
//     //         </form>
//     //         </body>
//     //         <div>
//     //             {user}<br/>
//     //             {password}
//     //         </div>
//     //         <footer>
//     //             <p>Dashboard</p>
//     //             <Link to="/dashboard">Dashboard</Link>
//     //         </footer>
//     //     </div>
//     )
// }
//
// const divBackground = {
//     'backgroundColor': '#282c34',
//     'minHeight': '100vh',
//     'display': 'flex',
//     'flexDirection': 'column',
//     'alignItems': 'center',
//     'justifyContent': 'center',
//     'fontSize': 'calc(10px + 2vmin)',
//     'color': 'white'
// };
//
// export default LoginPage2
