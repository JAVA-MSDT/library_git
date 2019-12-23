import React, {
    useState,
    useEffect
} from 'react';

const UserPage = (props) => {

    const location = props.location;
    const userId = location.state.userId;

    const [userInfo, setUserInfo] = useState({
        id: 0,
        name: "",
        lastName: "",
        email: "",
        login: "",
        password: "",
        role: "READER",
        blocked: false
    })

    const fetchUserById = async() =>{
        await fetch(`/library/user/${userId}`)
        .then(Response => Response.json())
        .then(result => setUserInfo(result))
    }

    useEffect(() => {
        fetchUserById();
    })

    return (
        <>
            <div className="container-fluid" style={{ 'backgroundColor': 'gray', 'height': '200vh' }}>
                <h1> User Page</h1>
                <h2> {userInfo.id} + " : "+ {userInfo.name} + " : " +{userInfo.email}</h2>

            </div>
        </>
    );
}

export default UserPage;