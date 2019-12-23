import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const BooksDisplay = (props) => {

    const books = props.books
    const searchQueryText = props.searchQuery;

    const userDtails = JSON.parse(localStorage.getItem("userInfo"));

    const [booksList, setBooksList] = useState([])
    const [searchQuery, setSearchQuery] = useState("");

    function booksFilter(book) {
       return book.name
            .toLowerCase()
            .includes(searchQuery.toLowerCase())
    }

    useEffect(() => {
        setSearchQuery(searchQueryText);
        setBooksList(books);
    },[searchQueryText, books])

    return (
        <>
            <div className="table-responsive">
                <table className="table table-hover table-dark container-fluid">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Book Title</th>
                            <th scope="col">Book Availibility</th>
                            <th scope="col">Book Page</th>

                            {(userDtails !== null && userDtails.role !== "READER") &&
                                <>
                                    <th scope="col">Edit Book</th>
                                    {userDtails.role === "ADMIN" &&
                                        <th scope="col">Delete Book</th>
                                    }
                                </>
                            }
                        </tr>
                    </thead>
                    {
                        
                        booksList.filter(book => booksFilter(book)).map((book, i) => (
                            <tbody key={book.id} >
                                <tr>
                                    <th scope="row">{i + 1}</th>
                                    <td>{book.name}</td>

                                    <td>{book.quantity > 0 ? <p>Availibale</p> : <p>Not Availibale</p>}</td>
                                    <td>
                                        <Link className="btn btn-outline-info btn-block"
                                            to={{ pathname: `/book/${book.name}`, state: { bookId: book.id } }}
                                        >
                                            Book Page
                                    </Link>
                                    </td>
                                    {(userDtails !== null && userDtails.role !== "READER") &&
                                        <>
                                            <td>
                                                <Link
                                                    className="btn btn-outline-warning btn-block"
                                                    to={{ pathname: `/book/edit-book`, state: { bookId: book.id } }}
                                                >
                                                    Edit
                                         </Link>
                                            </td>
                                            {userDtails.role === "ADMIN" &&
                                                <td>
                                                    <button
                                                        className="btn btn-outline-danger btn-block"
                                                        onClick={() => { props.deleteBook(book.id) }}
                                                    >
                                                        Delete
                                         </button>
                                                </td>
                                            }
                                        </>
                                    }
                                </tr>

                            </tbody>


                        ))
                    }
                </table>
            </div>
        </>

    )
}
export default BooksDisplay;