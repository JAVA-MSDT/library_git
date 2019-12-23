import React from 'react';
import logo from '../../images/logo512.png'
const User = (props) => {

    const userInfo = JSON.parse(localStorage.getItem("userInfo"));


    return (
        <>


            <article className="container-fluid">
                <div className="row justify-content-center bg-info py-2">
                <h1> User Page After Login!!</h1>
                </div>
                <div className="row justify-content-center bg-warning">
                    <section className="col-sm-10 col-md-4 border-md-right">
                        <div className="card container-fluid text-white bg-primary">
                            <div className="card-header container bg-info"><h3>{userInfo.name} {userInfo.lastName}</h3></div>
                            <div className="card-body">
                                <img src={logo} className="card-img border rounded-circle" alt="..." />
                            </div>
                        </div>
                    </section>
                    <section className="col-sm-10 col-md-8">
                    <div className="">
                        
                        <h2> user Name: {userInfo.name}</h2>
                        <h2> user email: {userInfo.email}</h2>
                        <h2> user email: {userInfo.role}</h2>
                        </div>
                    </section>
                </div>
            </article>






















            {/* 
        <div className="container-fluid">
                <div className="row justify-content-center">
                    <section className="col-sm-10 col-md-6 col-lg-4">
                        <div className="card container-fluid text-white bg-primary">
                            <div className="card-header container-fluid bg-info"><h3>{userInfo.name} {userInfo.lastName}</h3></div>
                            <div className="card-body">
                            <img src={logo} className="card-img" alt="..." />
                            </div>
                        </div>
                    </section>

                    <section className="col-sm-10 col-md-6 col-lg-8 d-flex justify-content-center">
                        <div className="">
                        <h1> User Page After Login!!</h1>
                        <h2> user Name: {userInfo.name}</h2>
                        <h2> user email: {userInfo.email}</h2>
                        <h2> user email: {userInfo.role}</h2>
                        </div>
                    </section>
                </div>
            </div>
            */}


        </>
    )
}

export default User;