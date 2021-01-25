import axios from "axios";

export const HelloButton = () => {
    return (
        <button onClick={() => {
            if (localStorage.key(0) === 'jwt') {
                axios.get('hello',
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                        },
                    }).then(response => {
                    console.log(response)
                }).catch(error => {
                    console.log(error);
                })
            } else{
                console.log("Please log in first!")
            }
        }}>
            HELLOCOMPONTENT
        </button>
    );
};
