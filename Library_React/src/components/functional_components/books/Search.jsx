import React from 'react';

const Search = (props) => {

    return (
        <>
            <form >
            
                        <input
                         type="text"
                         className="form-control form-control-lg border border-info" 
                         id="colFormLabelLg" 
                         placeholder="Search...."
                         onChange={e => props.searchBook(e.target.value)} />
        
            </form>
        </>
    )
}

export default Search;