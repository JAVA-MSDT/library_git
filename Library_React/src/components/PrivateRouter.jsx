import React from 'react';
import { Route, Redirect } from "react-router-dom";
import decode from 'jwt-decode';

const PrivateRouter = ({ component: Component, ...rest }) => {

    const Auth = () => {

        const token = localStorage.getItem('token');
        if (!token) {
          return false;
        }
      
        try {
  
          const { exp } = decode(token);
      
          if (exp < new Date().getTime() / 1000) {
            return false;
          }
      
        } catch (e) {
          return false;
        }
      
        return true;
      }

    return (
        <Route {...rest}
         render={(props) => (
            Auth ? (
            <Component {...props} /> )
            : (
                <Redirect
                to="/login"
                  />
            )
        )}
        />
    )
}

export default PrivateRouter;