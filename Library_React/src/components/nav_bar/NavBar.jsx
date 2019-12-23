import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import UserNavBar from './UserNavBar';
import AdminstrationNavBar from './AdminstrationNavBar';
import { USER_INFO, IS_LOGGED_IN, USER_ROLE } from '../../js/Constants';

const NavBar = (props) => {

    const userDetails = props.userDetails;

    const [isUserIn, setIsUserIn] = useState(sessionStorage.getItem(IS_LOGGED_IN));
    const [userRole, setUserRole] = useState(sessionStorage.getItem(USER_ROLE));

    const handleLogOut = () => {

        localStorage.removeItem(USER_INFO);
        sessionStorage.setItem(IS_LOGGED_IN, "");
        sessionStorage.setItem(USER_ROLE, "");

        setIsUserIn(sessionStorage.getItem(IS_LOGGED_IN));
        setUserRole(sessionStorage.getItem(USER_ROLE));
        alert("Log In Display");
    }

    return (
        <>
            <nav className="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
                < Link className="navbar-brand" to='/'>Library</Link>
                <button className="navbar-toggler" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            < Link className="nav-link" to='/'>Home <span className="sr-only">(current)</span></Link>
                        </li>
                        <li className="nav-item">
                            < Link className="nav-link" to='/books'>Book Store</Link>
                        </li>

                        {(isUserIn && userRole === "READER") ? <UserNavBar userData={userDetails} /> : ""}
                        {(isUserIn && userRole === "ADMIN") && <AdminstrationNavBar />}
                        {(isUserIn && userRole === "LIBRARIAN") && <AdminstrationNavBar />}


                    </ul>
                    <div className="form-inline my-2 my-lg-0">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item dropdown">
                                < Link className="btn btn-outline-success my-2 my-sm-0 dropdown-toggle" to='/' id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Languages
                                </Link>
                                <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                                    < Link className=" dropdown-item" to='/'>English</Link>
                                    < Link className=" dropdown-item" to='/'>Russian</Link>
                                    < Link className=" dropdown-item" to='/'>Arabic</Link>
                                </div>
                            </li>
                        </ul>
                        {isUserIn && <Link
                            className="btn btn-outline-success my-2 my-sm-0"
                            to='/login' style={{ 'marginLeft': '20px' }}
                            onClick={handleLogOut}
                        >Log Out
                                      </Link>}

                        {!isUserIn && <Link
                            className="btn btn-outline-success my-2 my-sm-0"
                            to='/login' style={{ 'marginLeft': '20px' }}
                        >LogIn
                                         </Link>}


                    </div>
                </div>
            </nav>
        </>
    )
};

export default NavBar;