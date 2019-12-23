import React, { useState, useEffect } from 'react';
import UserOrderDisplay from './userComponents/UserOrderDisplay';

const UserOrders = (props) => {

    const userInfo =  props.userData;  
    const [userId] = useState(userInfo.id);
    const [userOrders, setUserOrders] = useState([]);


    useEffect(() => {
        const fetchData = async () => {
            const result = await fetch(`/library/user/${userId}/orders`);
            const body = await result.json();
            setUserOrders(body);
        }

        fetchData();
    }, [userId])
   
    return (
        <>

        <UserOrderDisplay orders={userOrders} />
            
        </>
    )
}

export default UserOrders;