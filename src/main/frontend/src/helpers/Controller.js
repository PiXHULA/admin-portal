import axios from "axios";

class Controller {
    constructor() {
        this.authenticated = false;
        this.loading = true;
    }

    login(user, password, cb) {
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

    logout(cb) {
        this.authenticated = false;
        this.loading = true;
        cb();
    }

    isAuthenticated() {
        return this.authenticated;
    }

    isLoading() {
        return this.loading;
    }

    setLoadingTrue(cb) {
        this.loading = true;
        cb();
    }

    editUser({id, username, password}, cb) {
        axios.patch('api/v1/user/edit', {
                id: id,
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
            localStorage.removeItem("user")
            cb();
        }).catch(error => {
            console.log(error);
        });
    }

    createUser(user, cb) {
        axios.post(`api/v1/user/post`,
            user,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {

            console.log(response.data)
            cb();
        }).catch(error => {
            console.log(error);
        })
    }

    deleteUser (user, cb) {
        axios.delete(`api/v1/user/delete/${user.id}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            console.log(response.data)
            cb()
        }).catch(error => {
            console.log(error.response.data);
        })
    }

    getUsers (cb) {
        axios.get(`api/v1/user/users`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            console.log("GET ALL USERS")
            console.log(response.data)
            this.loading = false;
            cb(response.data)
        }).catch(error => {
            console.log(error.response.data);
        })
    }

    getUser (cb) {
        axios.get(`api/v1/user/user/${localStorage.getItem("user")}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            cb(response.data)
            console.log(response.data)
        }).catch(error => {
            console.log(error.response.data);
        })
    }

    getLoggedInUser (cb) {
        axios.get(`api/v1/user/user`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(response => {
            console.log(response.data)
            cb(response.data)
        }).catch(error => {
            console.log(error)
        })
    }


}

export default new Controller();
