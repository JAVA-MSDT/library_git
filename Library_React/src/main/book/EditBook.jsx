import React, {
    useState,
    useEffect
} from 'react';
import PageNotFound from '../commonPages/NotFoundPage.jsx';

const EditBook = (props) => {

    const location = props.location;
    const bookId = location.state.bookId;

    const [books, setBooks] = useState([]);
    const [id, setBookId] = useState(0)
    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState(0);

    const fetchBooks = async () => {
        await fetch('/library/books')
            .then(Response => Response.json())
            .then(results => setBooks(results))
    }

    const book = books.find(bookExist => bookExist.id === bookId);

   alert(id + " +:+ " + name + " +:+ " + quantity + " +==+ " + bookId)
    const editBook = async () => {
        await fetch('/library/book/edit',
            {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id,
                    name,
                    quantity
                })
            })
            .then(window.alert(id + " : " + name + " : " + quantity))
            .catch(err => window.alert(err));
    }

    useEffect(() => {
        fetchBooks();
    }, []);

    if (!book) return <PageNotFound />;
    return (
        <>
            <div className="container">
                <h1> Edit Book </h1>
                <br />
                <form onSubmit={() => editBook()}>
                    <div className="form-group">
                        <label htmlFor="formGroupExampleInput">Boot Title</label>
                        <input type="text"
                            className="form-control"
                            id="formGroupExampleInput"
                            placeholder="Boot Title"
                            value={book.name}
                            onChange={(event) => setName(event.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="formGroupExampleInput2">Book Quantity</label>
                        <input type="number"
                            className="form-control"
                            id="formGroupExampleInput2"
                            placeholder="Book Quantity"
                            value={book.quantity}
                            onChange={(event) => setQuantity(event.target.value)}

                        />
                    </div>
                    <input type="hidden"
                            className="form-control"
                            id="formGroupExampleInput2"
                            placeholder="Book Id"
                            value={book.id}
                            onChange={(event) => setBookId(event.target.value)}

                        />
                    <div className="col-auto">
                        <button
                            type="submit"
                            className="btn btn-primary mb-2">
                            Submit
                              </button>
                    </div>
                </form>
            </div>
        </>
    )
}

export default EditBook; 