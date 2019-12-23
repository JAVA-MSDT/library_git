import React, { useState, useEffect } from 'react';

const UserbookDisplay = (props) => {

    const orderId = props.order.orderId;
    const [orderBooks, setOrderBooks] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await fetch(`/library/user/orders/${orderId}/book`);
            const body = await result.json();
            setOrderBooks(body);
        }

        fetchData();
    }, [orderId])

    return (
        <>
                <select className="form-control form-control-lg rounded-pill" id="exampleFormControlSelect1">
                    {orderBooks.map((book, i) => (
                        <option key={i}>{book.name}</option>
                    ))}
                </select>
        </>
    )
}

export default UserbookDisplay;