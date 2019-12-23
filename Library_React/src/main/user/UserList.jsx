import React, {
    useState,
    useEffect
} from 'react';
import UserDisplay from './userComponents/UserDisplay.jsx';

const UserList = () => {


    const [usersList, setUsersList] = useState([]);

    const fetchUSers = async () => {
        await fetch('/library/users')
            .then(res => res.json())
            .then(result => setUsersList(result))
            .catch(err => window.alert("Unable to connect the server at the moment....."))
    }

    useEffect(() => {
        fetchUSers();
    }, [])

    return (
        <>
            <div className="container-fluid">
                <div className="row justify-content-center py-4">
                    <h1> User List !!</h1>
                </div>
                <div className="row px-4">
                    <UserDisplay users={usersList} />
                </div>
            </div>
        </>
    )
}

export default UserList;