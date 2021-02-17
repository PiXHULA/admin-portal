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
            const token = response.data.jwt;
            localStorage.setItem("jwt", token)
            this.authenticated = true;
            cb();
        }).catch(error => {
            alert(error.response.data)
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

    setLoadingFalse() {
        this.loading = false;
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
        ).then(() => {
            localStorage.removeItem("user")
            cb();
        }).catch(error => {
            alert(error.response.data)
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
            }).then(() => {
            cb();
        }).catch(error => {
            alert(error.response.data)

        })
    }

    deleteUser (user, cb) {
        axios.delete(`api/v1/user/delete/${user.id}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                },
            }).then(() => {
            cb()
        }).catch(error => {
            alert(error.response.data)
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
            this.loading = false;
            cb(response.data)
        }).catch(error => {
            alert(error.response.data)
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
        }).catch(error => {
            alert(error.response.data)
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
            this.loading = false;
            cb(response.data)
        }).catch(error => {
            alert(error.response.data)
        })
    }
}

export default new Controller();
