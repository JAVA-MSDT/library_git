import React from 'react';
import Carousel from 'src/components/functional_components/books/Carousel';

const HomePage = () => {



    return (
        <div className="container-fluid">
            <div className="row justify-content-center my-5">
                <h1> Home Page!!!</h1>
            </div>
            <div className="row justify-content-center px-1">
                <Carousel />
            </div>
        </div>

    );
}

export default HomePage;