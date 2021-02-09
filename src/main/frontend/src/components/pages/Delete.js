import React, {useEffect, useState} from "react";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";

const Delete = () => {

    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }

    const [userList, setUserList] = useState([]);

    useEffect(() => {
        controller.getUsers((response) => {setUserList(response)})
    }, [...userList]);

    const getUserList = () => {
        return (
            <ul>
                {[...userList].filter(user => user.roles[0].roleName === "ADMIN").map((user) => (

                    <li>
                        {user.username}
                        <button onClick={() => {
                            controller.deleteUser(user,
                                () => {controller.getUsers((res) => {setUserList(res)})
                            })
                        }}>Delete
                        </button>
                    </li>
                ))}
            </ul>
        )
    }

    return (
        <div>
        <h2>Delete user</h2>
        {getUserList()}
            <button type="button" onClick={handleClick}>
                Go back!
            </button>
        </div>
    );
}
export default Delete;
