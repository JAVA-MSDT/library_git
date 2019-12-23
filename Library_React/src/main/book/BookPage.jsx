import React,
{
    useState,
    useEffect
} from 'react';
import NotFoundPage from '../commonPages/NotFoundPage.jsx';
import '../../css/SingleBook.css';
import image from '../../images/logo512.png';


const BookPage = (props) => {
    
    const location = props.location;
    const bookId = location.state.bookId;

    const [bookInfo, setBookInfo] = useState({
        id: 0,
        name: "undefined_book",
        quantity: 0,
        orders: [],
        version: 0
    });

    useEffect(() => {
        const fetchData = async () => {
            const result = await fetch(`/library/book/${bookId}`);
            const body = await result.json();
            setBookInfo(body);
        }

        fetchData();
    }, [bookId])

    
    if (bookInfo.id === 0) return <NotFoundPage />

    return (
        <>
            <div className="container">
                <div className='row book-row'>
                    <div className="col-4 image-col">
                        <img src={image} className="img-thumbnail" alt="..." />
                    </div>
                    <div className="col-8 info-col" style={{ 'backgroundColor': 'YELLOW' }}>
                        <div className="row info-row">
                            <div className="col-8 info-col-inner">
                                <h2> Book Name: {bookInfo.name}</h2>
                                <h2> Book Quantity: {bookInfo.quantity} </h2>
                            </div>
                            <div className="col-4 button-col">
                                <button className="btn btn-primary btn-lg btn-block">Add To Bin</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </>
    )
}
export default BookPage; 