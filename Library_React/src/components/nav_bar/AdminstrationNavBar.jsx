import React from 'react';
import {Link} from 'react-router-dom';
import { USER_INFO } from '../../js/Constants';

const AdminstrationNavBar = () => {

    const userDetails = JSON.parse(localStorage.getItem(USER_INFO));

    return (
        <>
                        <li className="nav-item active">
                            < Link className="nav-link" to='/user'>Adminstration Profile <span className="sr-only">(current)</span></Link>
                        </li>
                        <li className="nav-item">
                            < Link className="nav-link" to='/users'>User List</Link>
                        </li>
                        <li className="nav-item">
                            < Link className="nav-link" to='/orders'>Order List</Link>
                        </li>
                        <li className="nav-item">
                < Link className="nav-link" to={{ pathname: `/${userDetails.name}/orders`, state: { userId: userDetails.id } }}>{userDetails.name} Orders</Link>
            </li>

        </>
    )
}

export default AdminstrationNavBar;