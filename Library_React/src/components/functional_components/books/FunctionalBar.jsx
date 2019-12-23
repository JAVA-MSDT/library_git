import React from 'react';
import Search from './Search';
import Sort from './Sort';

const FunctionalBar = (props) => {

    function searchBook(query) {
        props.onQueryTextChange(query);
    }

    function sortBy(by) {
        props.sortBy(by);
    }

    function sortOrder(order) {
        props.sortOrder(order);
    }



    return (
        <>
            <div className="container-fluid border-info bg-success py-2" >
                <div className="row justify-content-center">
                    <div className="col-sm-12 col-md-4">
                       <Search searchBook={searchBook} />   
                    </div>
                    <div className="col-sm-12 col-md-6 mt-sm-2">
                        <Sort sortBy={sortBy} sortOrder={sortOrder} />
                    </div>
                </div>
            </div>
        </>
    )
}

export default FunctionalBar;