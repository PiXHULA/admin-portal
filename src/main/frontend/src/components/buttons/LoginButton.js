import axios from "axios";


export const LoginButton = () => {
    const user = {
        username: "admin",
        password: "password"
    }
    return (
        <button onClick={() => {
            axios.post('authenticate', user,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                }).then(response => {
                if (localStorage.getItem("jwt") === null) {
                    const token = response.data.jwt;
                    localStorage.setItem("jwt", token)
                    console.log("YOU HAVE LOGGED IN");
                    console.log(localStorage.getItem("jwt"));
                }
            }).catch(error => {
                console.log(error);
            })
        }}>
            LoginCOMPONENT
        </button>
    );
}
