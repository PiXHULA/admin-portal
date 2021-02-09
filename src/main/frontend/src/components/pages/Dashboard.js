import React, {useEffect, useState} from 'react';
import auth from "../../helpers/Auth";
import axios from 'axios';


const Dashboard = (props) => {

    const [userList, setUserList] = useState([]);

    const getUsers = () => {

        axios.get(`api/v1/user/users`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            console.log("GET ALL USERS")
            console.log(response)
            setUserList(response.data)
        }).catch(error => {
            console.log(error);
        })
    }

    // const checkUserList = userList.map(user => {
    //     `${user.id},${user.name}, ${user.password}, ${deletable = user.roles[0].roleName === "SUPERADMIN" ? false : true}`
    // });
    // console.log(checkUserList);

    // const checkUserList = () => {
    //     userList.map(value => {
    //         if (user.roles[0].roleName === "SUPERADMIN") {
    //             value = {id = value.id,
    //                 username = value.username,
    //                 password = value.password,
    //                 deletable = false
    //             }
    //         }
    //     })
    // }


    useEffect(() => {
        getUsers()
    }, []);


    const deleteUser = (user,cb) => {
        axios.delete(`api/v1/user/delete/${user.id}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            console.log("GET USER " + user.username)
            console.log(response)
            setUserList(response.data)
            cb()
        }).catch(error => {
            console.log(error);
        })
    }

    const getUserList = () => {
        return (
            <ul>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                        <button onClick={() => {
                            localStorage.setItem("user",user.username)
                            props.history.push("/edit")
                        }}>Edit
                        </button>
                        <button onClick={()=>{
                            deleteUser(user,()=>{
                                getUsers()
                            })
                        }}>Delete</button>
                    </li>
                ))}
            </ul>
        )
    }

    return (<div>
        <h2>Dashboard</h2>

        {getUserList()}
        <button onClick={() => {
            if (localStorage.length > 0) {
                auth.logout(() => {
                    console.log("YOU HAVE LOGGED OUT");
                    localStorage.clear();
                    props.history.push("/")
                })
            }
        }}>
            Logout
        </button>
    </div>);
};


export default Dashboard;
