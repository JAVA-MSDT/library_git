import React from 'react';
import UserBookDisplay from './UserBookDisplay';
const UserOrderDisplay = (props) => {

    const userOrders = props.orders;
    return (
        <>
            <div className="container-fluid ">
                <div className="row justify-content-center my-4">
                    <h1> Order Display User</h1>
                </div>
                <div className="row">
                    {userOrders.map((order, i) => (
                        <div className="container-fluid" key={i}>
                            <div className="accordion" id="accordionExample">
                                <div className="card">
                                    <button className="card-header bg-primary btn btn-link"
                                        id="headingOne"
                                       
                                        data-toggle="collapse"
                                        data-target="#collapseOne"
                                        aria-expanded="true"
                                        aria-controls="collapseOne"
                                    >
                                        <h2 className="text-light float-left"> Order No: {i + 1} </h2>

                                    </button>

                                    <div id="collapseOne"
                                        className="collapse show bg-info"
                                        aria-labelledby="headingOne"
                                        data-parent="#accordionExample">
                                        <div className="card-body">

                                            <table className="table table-striped table-dark text-left">
                                                <thead>
                                                    <tr>
                                                        <th scope="col"><h2>Order Date: </h2> </th>
                                                        <th scope="col"><h4> {order.orderDate} </h4> </th>
                                                    </tr>
                                                    <tr>
                                                        <th scope="col"><h2>Returning Date: </h2> </th>
                                                        <th scope="col"><h4> {order.returningDate} </h4> </th>
                                                    </tr>
                                                    <tr>
                                                        <th scope="col"><h2>Reading Place: </h2> </th>
                                                        <th scope="col"><h4> {order.readingPlace} </h4> </th>
                                                    </tr>
                                                    <tr>
                                                        <th scope="col"><h2> Books </h2> </th>
                                                        <th scope="col"><h4>  <UserBookDisplay order={order} /> </h4> </th>
                                                    </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>



        </>
    )
}

export default UserOrderDisplay;