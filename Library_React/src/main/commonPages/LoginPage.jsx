import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import loginImage from '../../images/epam.jpg';
import { IS_LOGGED_IN, USER_ROLE, USER_INFO } from '../../js/Constants';

const LoginPage = (props) => {

    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const userDetails = JSON.parse(localStorage.getItem(USER_INFO))

    const validateForm = () => {
        return login.length > 0 && password.length > 0;
    }

    const submitLogin = (e) => {
        e.preventDefault();

        axios.post(`/library/login/parameter?login=${login}&password=${password}`, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                "Content-type": "application/json; charset=UTF-8",
            },
            credentials: 'same-origin'
        }).then(response => {
            console.log("Response Status Error Outside: " + response.status)
            alert("Response Status Outside: " + response.status)
            if (response.status === 200) {
                localStorage.setItem(USER_INFO, JSON.stringify(response.data));
                setIsLoggedIn(true);
            } else {
                setIsError(true);
                console.log("Response Status Error: " + response.status)
                alert("Response Status: " + response.status)
            }


        })
            .catch(err => console.log("An Error: " + err))
    }

    const errorMessage = () => {
        if (isError) {
            return <h1 style={{ 'color': 'red' }}> Login or Password is incorrect!!</h1>
        }
    }


    if (isLoggedIn) {
        sessionStorage.setItem(IS_LOGGED_IN, true);
        sessionStorage.setItem(USER_ROLE, userDetails.role);
        
        //const isIn = sessionStorage.setItem(IS_LOGGED_IN, true);
       // const userRole = sessionStorage.setItem(USER_ROLE, userDetails.role);
        // props.loogedStatus(isIn, userRole);
        props.userInfo(userDetails);
        return <Redirect to={{ pathname: `/${userDetails.name}`, props: { userData: userDetails } }} />
    }

    return (
        <>

            <div className="container bg-light border rounded-lg my-5">
                <div className="row justify-content-center">
                    
                        <form className="form-signin border-info">
                            <div className="text-center mb-4">

                                <h1 className="my-4 font-weight-bold">Welcome Back</h1>
                                <img className="my-4 rounded-pill" src={loginImage} alt="" width="50%" height="50%" />
                            </div>
                            {errorMessage()}
                            <div className="form-label-group">
                                <label className="h3 my-2" htmlFor="inputEmail">Login</label>
                                <input
                                    type="text"
                                    name="login"
                                    value={login}
                                    id="inputEmail"
                                    className="form-control"
                                    placeholder="Login"
                                    required=""
                                    autoFocus=""
                                    onChange={e => { setLogin(e.target.value) }}
                                    autoComplete="true"
                                />

                            </div>

                            <div className="form-label-group">
                                <label className="h3 my-2" htmlFor="inputPassword">Password</label>
                                <input
                                    type="password"
                                    name="password"
                                    value={password}
                                    id="inputPassword"
                                    className="form-control"
                                    placeholder="Password"
                                    required=""
                                    onChange={e => { setPassword(e.target.value) }}
                                    autoComplete="true"
                                />

                            </div>

                            <div className="checkbox my-3">
                                <label>
                                    <input type="checkbox" value="remember-me" /> Remember me
                        </label>
                            </div>
                            <button
                                className="btn btn-lg btn-info btn-block"
                                type="submit"
                                onClick={e => submitLogin(e)}
                                disabled={!validateForm()}
                            >Sign in
                        </button>
                            <p className="mt-5 mb-3 text-muted text-center">© 2017-2019</p>
                        </form>
                    </div>
                </div>

        </>
    )
}




export default LoginPage;