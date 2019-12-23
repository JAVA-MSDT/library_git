import React from 'react';
import { Link } from 'react-router-dom';
//import '../css/CustomCard.css';


const UserDisplay = (props) => {



    const users = props.users;

    return (
        <>
        <div className="table-responsive">
            <table className="table table-hover table-dark container-fluid">
                <thead>
                    <tr className="text-center">
                        <th scope="col">#</th>
                        <th scope="col">First Name</th>
                        <th scope="col">Last Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Login</th>
                        <th scope="col">Password</th>
                        <th scope="col">Role</th>
                        <th scope="col">Block Status</th>
                        <th scope="col">User Page</th>
                    </tr>
                </thead>
                {
                    users.map((user, i) => (
                        <tbody key={user.id} >
                            <tr className="text-center">
                                <th scope="row">{i + 1}</th>
                                <td>{user.name}</td>
                                <td>{user.lastName}</td>
                                <td>{user.email}</td>
                                <td>{user.login}</td>
                                <td>{user.password}</td>
                                <td>{user.role}</td>
                                <td>{user.blocked ? "BLOCKED" : "NOT BLOCKED"}</td>
                                <td>
                                    <Link className="btn btn-outline-info btn-block"
                                        to={{ pathname: `/user/${user.name}`, state: { userId: user.id } }}
                                    >
                                        User Page
                                    </Link>


                                </td>
                            </tr>

                        </tbody>
                    ))
                }
            </table>
            </div>
            {users.length === 0 && <div>
             <h1> Unable to connect the server at the moment.....</h1>
             <h2> Please Try again later....</h2> </div>}
        </>

    )
}
export default UserDisplay;