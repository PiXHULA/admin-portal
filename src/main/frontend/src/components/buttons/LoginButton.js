import axios from "axios";


export const LoginButton = ({usern,password, btnText}) => {
    const user = {
        username: usern,
        password: password
    }
    console.log(user)
    return (
        <button
            style={buttonStyle}
            onClick={() => {
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
            {btnText}
        </button>
    );
}
const buttonStyle = {
    'backgroundColor': 'white',
    'minHeight': '200px',
    'fontSize': '20px',
    'borderRadius':'5%'
};
