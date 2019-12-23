import React from 'react';
import green from '../../../images/green.jpg';
import epam from '../../../images/epam.jpg';
import libraryHall from '../../../images/libraryHall.jpg';
import book from '../../../images/book.jpg';

const Carousel = () => {

    return (
        <>

            <article id="page-carousel" className="page-section">

                <section id="layout-carousel" className="layout-carousel carousel slide" data-ride="carousel">
                    <div className="carousel-inner">

                        <ol className="carousel-indicators">
                            <li data-target="#layout-carousel" data-slide-to="0" className="active"></li>
                            <li data-target="#layout-carousel" data-slide-to="1"></li>
                            <li data-target="#layout-carousel" data-slide-to="2"></li>
                            <li data-target="#layout-carousel" data-slide-to="3"></li>
                        </ol>

                        <section className="carousel-item active">
                            <img src={green} className="img-fluid d-block" alt="Green" style={{"height" : "500px","width" : "1920px" }}/>
                        </section>
                        <section className="carousel-item">
                            <img src={epam} className="img-fluid d-block" alt=" epam Logo" style={{"height" : "500px","width" : "1920px"}}/>
                        </section>
                        <section className="carousel-item">
                            <img src={libraryHall} className="img-fluid d-block" alt="libraryHall" style={{"height" : "500px","width" : "1920px"}}/>
                        </section>
                        <section className="carousel-item">
                            <img src={book} className="img-fluid d-block" alt="Books" style={{"height" : "500px","width" : "1920px"}}/>
                        </section>
                    </div>
                    <a className="carousel-control-prev" href="#layout-carousel" role="button" data-slide="prev">
                        <span className="carousel-control-prev-icon">
                            <span className="sr-only">Previous</span>
                        </span>
                    </a>

                    <a className="carousel-control-next" href="#layout-carousel" role="button" data-slide="next">
                        <span className="carousel-control-next-icon">
                            <span className="sr-only">Next</span>
                        </span>
                    </a>
                </section>

            </article>

            {/* 
            <article className="carousel-component">

                <section id="layout-carousel"
                    class="layout-carousel carousel slide"
                    data-ride="carousel">

                    <div className="carousel-inner">

                        <section className="carousel-item">
                            <img src={green} className="img-fluid d-block" alt="Green" />
                        </section>
                        <section className="carousel-item">
                            <img src={epam} className="img-fluid d-block" alt=" epam Logo" />
                        </section>
                        <section className="carousel-item">
                            <img src={libraryHall} className="img-fluid d-block" alt="libraryHall" />
                        </section>
                        <section className="carousel-item">
                            <img src={book} className="img-fluid d-block" alt="Books" />
                        </section>
                    </div>
                </section>

            </article>
            */}
        </>
    )
}

export default Carousel;