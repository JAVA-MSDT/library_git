import React, { useState, useEffect } from 'react'
import OrderDisplay from './orderComponent/OrderDisplay.jsx'

const OrderList = () => {

    const [orderList, setOrderList] = useState([]);

    const fetchOrders = async () => {
        await fetch('/library/orders')
            .then(res => res.json())
            .then(result => setOrderList(result))
            .catch(err => window.alert("Unable to connect the server at the moment....."));
    }

    useEffect(() => {
        fetchOrders();
    }, []);

    return (
        <>
            <div className="container-fluid">
                <div className="row justify-content-center py-4">
                    <h1> Order List !!!</h1>
                </div>
                <div className="row justify-content-center px-4">
                    <OrderDisplay orders={orderList} />
                </div>
            </div>
        </>

    )
};

export default OrderList;