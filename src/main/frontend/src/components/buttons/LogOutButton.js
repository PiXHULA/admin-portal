export const LogOutButton = () => {
    return (
        <button onClick={() => {
            if (localStorage.length > 0) {
                console.log("YOU HAVE LOGGED OUT");
                localStorage.clear();
            }
        }}>
            LogoutCOMPONTENT
        </button>
    );
};
