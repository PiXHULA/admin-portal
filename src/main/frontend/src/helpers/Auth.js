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
            console.log(response.data.jwt)
            const token = response.data.jwt;
            localStorage.setItem("jwt", token)
            this.authenticated = true;
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