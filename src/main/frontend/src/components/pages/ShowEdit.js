import React, {useEffect, useState} from "react";
import controller from "../../helpers/Controller";
import {useHistory} from "react-router-dom";
import Nav from "./Nav";

const ShowEdit = () => {

    let history = useHistory();

    const handleClick = () => {
        controller.setLoadingTrue(() =>history.push("/dashboard"))
    }

    const editClick = () => {
        controller.setLoadingTrue(() =>history.push("/edit"))
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
        {controller.isLoading() ?
            <h2>Loading...</h2> :
            getUserList()}
        {controller.isLoading() ?
            <h2>Loading...</h2> :
            <button type="button" onClick={handleClick}>
                Go back!
            </button>}
        </div>
    );
}
export default ShowEdit;
