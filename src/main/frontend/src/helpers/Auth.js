import axios from "axios";

class Auth {
    constructor() {
        this.authenticated = false;
    }

     login(user,password,cb) {
         axios.post('authenticate', {
                username: user,
                password: password
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            },
        ).then((response) => {
             console.log("LOGIN")
            console.log(response.data)
            const token = response.data.jwt;
            localStorage.setItem("jwt", token)
            this.authenticated = true;
            cb();
        }).catch(error => {
            console.log(error);
        });
    }

     editUser({id,username,password}, cb)  {
        axios.patch('api/v1/user/edit', {
            id:id,
            username: username,
            password: password
            }, {
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
            },
        ).then((response) => {
            console.log("EDIT")
            console.log(response.data)
            cb();
        }).catch(error => {
            console.log(error);
        });
    }


logout(cb)
{
    this.authenticated = false;
    cb();
}

isAuthenticated()
{
    return this.authenticated;
}
}

export default new Auth();
