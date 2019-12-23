import React,
{
    useState,
    useEffect
} from 'react';
import { Link } from 'react-router-dom';
import BooksDisplay from './components/BooksDisplay.jsx';
import FunctionalBar from '../../components/functional_components/books/FunctionalBar.jsx';
import { SORT_ASCENDING, SORT_DESCENDING, USER_INFO, SORT_BY_TITLE, SORT_BY_QUANTITY } from '../../js/Constants.js';
import Pagination from '../../components/pagination/Pagination.jsx';

const BookStore = () => {

    const [data, setBooks] = useState([]);
    const userDtails = JSON.parse(localStorage.getItem(USER_INFO));
    const [searchText, setSearchText] = useState("");
    const [sortBy, setSortBy] = useState("");
    const [sortOrder, setSortOrder] = useState("")

    const [currentPage, setCurrentPage] = useState(1);
    const [booksPerPage, setBooksPerPage] = useState(10);

    // Getting Curent Posts Per Page
    const indexOfLastBook = currentPage * booksPerPage;
    const indexOfFirstBook = indexOfLastBook - booksPerPage;
    const currentBooks = data.slice(indexOfFirstBook, indexOfLastBook);

    const deleteBook = (bookId) => {
        const confirming = window.confirm("Are you sure that You want to delete that Book??")
        // eslint-disable-next-line
        if (confirming == true) {
            fetch(`/library/book/delete/${bookId}`, {
                method: 'delete'
            });
            alert("Deleted")
        }
        fetchBooks();
    }

    function queryText(query) {
        setSearchText(query);

    }

    function sortByCriteria(by) {
        setSortBy(by);
    }

    const sortOrderCriteria = (order) => {
        setSortOrder(order);
    }

    const fetchBooks = async () => {
        await fetch('/library/books')
            .then(Response => Response.json())
            .then(result => setBooks(result));

    };

    const fetchBooksSortedByName = async () => {

        await fetch('/library/books/sortName')
            .then(Response => Response.json())
            .then(result => setBooks(result));

    };

    const fetchBooksSortedByQuantity = async () => {

        await fetch('/library/books/sortQuantity')
            .then(Response => Response.json())
            .then(result => setBooks(result));

    };

    const sortBooks = (bookA, bookB) => {

        const a = bookA[sortBy];
        const b = bookB[sortBy];
        let comaprision = 0;

        if (a > b) {
            comaprision = 1;
        } else if (a < b) {
            comaprision = -1;
        }
        return comaprision;
    }

    const bookSorting = () => {

        if (sortBy === SORT_BY_TITLE) {
            fetchBooksSortedByName();
        } else if (sortBy === SORT_BY_QUANTITY) {
            fetchBooksSortedByQuantity();
        }
    }
    
    useEffect(() => {
        bookSorting();
    })

    useEffect(() => {
        fetchBooks();
    }, [])



    const perPage = (perPage) => {
        setBooksPerPage(perPage);
    }

    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber);
    }


    return (
        <>
            <div className="container-fluid">
                <div className="row">
                    <FunctionalBar onQueryTextChange={queryText} sortBy={sortByCriteria} sortOrder={sortOrderCriteria} />
                </div>
                <div className="row justify-content-center my-4">
                    <h1> Book Store !!</h1>
                </div>
                <div className="row justify-content-center mb-4">
                    <div className="col-4">
                        {(userDtails !== null && userDtails.role !== "READER") &&
                            <Link
                                className="btn btn-outline-success bg-info text-white btn-block"
                                to='/book/add-book'>
                                Add Book
                            </Link>
                        }
                    </div>

                </div>
                <div className="row px-4">
                    <Pagination totalItems={data.length} itemPerPage={booksPerPage} perPage={perPage} paginate={paginate} />
                </div>
                <div className="row px-4">
                    <BooksDisplay books={currentBooks}
                        deleteBook={deleteBook}
                        searchQuery={searchText}
                        />
                </div>
                <div className="row px-4">
                    <Pagination totalItems={data.length} itemPerPage={booksPerPage} perPage={perPage} paginate={paginate} />
                </div>
            </div>

        </>
    )
}

export default BookStore;