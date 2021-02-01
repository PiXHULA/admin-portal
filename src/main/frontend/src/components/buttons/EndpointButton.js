import axios from "axios";

export const EndpointButton = ({btnText,endp}) => {
    return (
        <button onClick={() => {
            if (localStorage.key(0) === 'jwt') {
                axios.get(`${endp}`,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                        },
                    }).then(response => {
                    console.log(response)
                    console.log(response.data)
                }).catch(error => {
                    console.log(error);
                })
            } else{
                console.log("Please log in first!")
            }
        }}>
            {btnText}
        </button>
    );
};
