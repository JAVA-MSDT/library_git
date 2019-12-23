import React from 'react';
import { SORT_BY_TITLE, SORT_BY_QUANTITY,
     SORT_ASCENDING, SORT_DESCENDING } from '../../../js/Constants';

const Sort = (props) => {


    return (
        <>
            <div className="container">
                <div className="row justify-content-center">
                    <div className="col-xs-12 col-lg-6">
                            <select
                             className="form-control" 
                             id="exampleFormControlSelect1"
                             onChange = {e => props.sortBy(e.target.value)}>
                                <option value="">Sort By</option>
                                <option value={SORT_BY_TITLE}>Title </option>
                                <option value={SORT_BY_QUANTITY}>Quantity</option>
                            </select>
                    </div>
                    <div className="col-xs-12 col-lg-6">
                            <select className="form-control" 
                            id="exampleFormControlSelect1"
                            onChange = {e => props.sortOrder(e.target.value)}
                            >
                                <option value="">Sort Order</option>
                                <option value={SORT_ASCENDING}>Asc</option>
                                <option value={SORT_DESCENDING}>Desc</option>
                            </select>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Sort;