import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const LogingButton = (props) => {

    const [userDetails] = useState(props.userData);
    const [changeLogButton, setChangeLogButton] = useState(true);

    const handleLogOut = () => {

        localStorage.removeItem("userInfo");
        setChangeLogButton(true);
        console.log("User Loged out!!")

        if(userDetails === null){
        
        }
    }

    const handleLogIn = () => {
        if (userDetails !== null) {
        props.userRole(userDetails.role)
        setChangeLogButton(false);
        console.log("User Loged out!!")
        }
    }

    return (
        <>
            {!changeLogButton && 
            <Link
                className="btn btn-outline-success my-2 my-sm-0"
                to='/login' style={{ 'marginLeft': '20px' }}
                onClick={handleLogOut}
            >
                Log Out
            
            </Link>}

            {changeLogButton &&
             <Link
                className="btn btn-outline-success my-2 my-sm-0"
                to='/login' style={{ 'marginLeft': '20px' }}
                onClick={handleLogIn}
                >
                    LogIn
             
              </Link>}
        </>
    )
}
export default LogingButton;