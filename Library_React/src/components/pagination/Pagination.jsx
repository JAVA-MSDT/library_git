import React from 'react';

const Pagination = (props) => {

    const totalItems = props.totalItems;
    const itemPerPage = props.itemPerPage;

    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalItems / itemPerPage); i++) {
        pageNumbers.push(i);
    }

    let buttons = document.querySelectorAll(".itemPerPage > button");

    for (let i = 0; i < buttons.length; i++) {
        alert("Buttons Length: " + buttons.length + " : " + buttons[i].value);
    }


    return (
        <>
            <nav>
                <ul className="pagination">
                    <li>
                        <div className="btn-group">
                            <button className="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Per Page
                            </button>
                            <div className="itemsPerPage dropdown-menu" id="page-number">
                                <button onClick={() => props.perPage(totalItems)} className="dropdown-item" href="#">All</button>
                                <button onClick={() => props.perPage(5)} className="dropdown-item" href="#">5</button>
                                <button onClick={() => props.perPage(10)} className="dropdown-item" href="#">10</button>
                                <button onClick={() => props.perPage(15)} className="dropdown-item" href="#">15</button>
                                <button onClick={() => props.perPage(30)} className="dropdown-item" href="#">30</button>
                            </div>
                        </div>
                    </li>
                    {pageNumbers.map(number => (
                        <li key={number} className="page-item">
                            <button onClick={() => props.paginate(number)} className="page-link">
                                {number}
                            </button>
                        </li>
                    ))}
                </ul>
            </nav>
        </>
    )
}

export default Pagination;