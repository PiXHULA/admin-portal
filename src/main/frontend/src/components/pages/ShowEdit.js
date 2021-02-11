import React, {useEffect, useState} from "react";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";

const ShowEdit = () => {

    let history = useHistory();

    const handleClick = () => {
        history.push("/dashboard");
    }

    const editClick = () => {
        history.push("/edit");
    }

    const [userList, setUserList] = useState([]);

    useEffect(() => {
        controller.getUsers((response) => {setUserList(response)})
    }, []);

    const getUserList = () => {
        return (
            <ul>
                {[...userList].map((user) => (
                    <li>
                        {user.username}
                        <button onClick={() => {
                            localStorage.setItem("user", user.id)
                            editClick()
                        }}>Edit
                        </button>
                    </li>
                ))}
            </ul>
        )
    }

    return (
        <div>
        <h2>Edit user</h2>
        {getUserList()}
            <button type="button" onClick={handleClick}>
                Go back!
            </button>
        </div>
    );
}
export default ShowEdit;
