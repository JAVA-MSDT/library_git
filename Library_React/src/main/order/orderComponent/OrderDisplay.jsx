import React from 'react';
import { Link } from 'react-router-dom';
import UserbookDisplay from 'src/main/user/userComponents/UserBookDisplay';
import Moment from 'react-moment';

const OrderComponent = (props) => {

    const orders = props.orders;


    return (
        <>
            <div className="table-responsive">
                <table className="table table-hover table-dark container-fluid">
                    <thead>
                        <tr className="text-center">
                            <th scope="col">#</th>
                            <th scope="col">Reader Name</th>
                            <th scope="col">Reader Email</th>
                            <th scope="col">Books</th>
                            <th scope="col">Order Data</th>
                            <th scope="col">Returning Data</th>
                            <th scope="col">Reading Place</th>
                            <th scope="col">Books Resturned</th>
                            <th scope="col">Edit</th>
                        </tr>
                    </thead>
                    {orders.map((order, i) => (

                        <tbody key={i}>
                            <tr className="text-center">
                                <th scope="row">{i + 1}</th>
                                <td>{order.user.name}</td>
                                <td>{order.user.email}</td>
                                <td> <UserbookDisplay order={order} /></td>
                                <td>
                                    <Moment local format="YYYY/MM/DD">
                                        {order.orderDate}
                                    </Moment>
                                </td>
                                <td>
                                    <Moment local format="YYYY/MM/DD">
                                        {order.returningDate}
                                    </Moment>
                                </td>
                                <td>{order.readingPlace}</td>
                                <td>{!order.bookReturned ? "NO" : "YES"}</td>
                                <td>
                                    <Link className="btn btn-outline-info btn-block"
                                        to={{ pathname: `/`, state: { orderId: order.orderId } }}
                                    >
                                        Edit
                                    </Link>
                                </td>
                            </tr>
                        </tbody>
                    ))}


                </table>
            </div>

        </>
    )
}

export default OrderComponent;