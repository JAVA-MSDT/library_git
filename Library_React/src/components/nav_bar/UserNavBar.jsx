import React from 'react';
import { Link } from 'react-router-dom';
import { USER_INFO } from '../../js/Constants';

const UserNavBar = (props) => {

    const userDetails = JSON.parse(localStorage.getItem(USER_INFO));

   
    return (
        <>
            <li className="nav-item active">
                < Link className="nav-link" to={`/${userDetails.name}`}> {userDetails.name} Profile <span className="sr-only">(current)</span></Link>
            </li>
            <li className="nav-item">
                < Link className="nav-link" to={{ pathname: `/${userDetails.name}/orders`, state: { userId: userDetails.id}}}>{userDetails.name} Orders</Link>
            </li>

        </>
    )
}

export default UserNavBar;