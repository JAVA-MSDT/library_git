import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch
} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import 'jquery/dist/jquery.js';
import 'popper.js/dist/umd/popper.js';
import NavBar from '../components/nav_bar/NavBar.jsx';
import HomePage from './commonPages/HomePage.jsx';

/////////// Book Pages //////////////
import BookStore from './book/BookStore.jsx';
import BookPage from './book/BookPage.jsx';
import AddBook from '../main/book/AddBook.jsx';
import EditBook from './book/EditBook.jsx';

/////////// User Pages //////////////
import UserPage from './user/UserPage.jsx';
import UserList from './user/UserList.jsx';

import OrderList from './order/OrderList.jsx';
import NotFoundPage from './commonPages/NotFoundPage.jsx';
import mainImage from '../images/libraryHall.jpg';
import '../css/App.css';
import LoginPage from './commonPages/LoginPage.jsx';
import User from './user/User.jsx';
import UserOrders from './user/UserOrders.jsx';
import Footer from '../components/Footer.jsx';
import { USER_INFO } from '../js/Constants.js';

function App() {

  const [userDetails] = useState(JSON.parse(localStorage.getItem(USER_INFO)));

  function getUserInfo(userInfo) {
    alert("getUserInfo: " + userInfo)
    // setUserDetails(userInfo);
  }

  return (
    <>
      <div className="App container-fluid">
        <Router>
          <div className="row justify-content-center">
            <NavBar userData={userDetails} />
            <img src={mainImage} className="card-img" alt="..." />
          </div>
          <div className="page-body row justify-content-center bg-secondary">
            <Switch>
              <Route path='/' component={HomePage} exact />
              <Route path='/login' render={(props) => <LoginPage {...props} userInfo={getUserInfo} />} exact />

              <Route path='/books' component={BookStore} exact />
              <Route path='/book/add-book' component={AddBook} exact />
              <Route path='/book/edit-book' component={EditBook} exact />
              <Route path='/book/:title' component={BookPage} exact />

              <Route path='/users' component={UserList} exact />
              <Route path='/user/:name' component={UserPage} exact />

              <Route path='/orders' component={OrderList} exact />


              { /** Pages Appears after Loging in only  */}
              <Route path='/:name' render={(props) => <User {...props} userData={userDetails} />} exact />
              <Route path='/:name/orders' render={(props) => <UserOrders {...props} userData={userDetails} />} exact />
              <Route component={NotFoundPage} />
            </Switch>
          </div>      
          <div className="row bg-warning">
          <div className="footer container-fluid">
            <Footer />
          </div>
        </div>
        </Router>
      </div>
    </>
  );
}

export default App;