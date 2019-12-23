import React, { useState } from 'react';

const AddBook = (props) => {

    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState(0);

    const addBook = async () => { 
        await fetch('/library/book',
            {
                method: 'POST',
                headers: {
                    'Accept':'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name,
                    quantity
                })
            })
            .then(() => {this.props.history.push('/library/books')})
            .catch(err => window.alert(err));
    }

    return (
        <>
            <div className="container">
                <h1> Add Book </h1>
                <br/>
                <form onSubmit={() => addBook()}>
                    <div className="form-group">
                        <label htmlFor="formGroupExampleInput">Boot Title</label>
                        <input type="text"
                            className="form-control"
                            id="formGroupExampleInput"
                            placeholder="Boot Title"
                            value={name}
                            onChange={(event) => setName(event.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="formGroupExampleInput2">Book Quantity</label>
                        <input type="number"
                            className="form-control"
                            id="formGroupExampleInput2"
                            placeholder="Book Quantity"
                            value={quantity}
                            onChange={(event) => setQuantity(event.target.value)}

                        />
                    </div>
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

export default AddBook; 