const BLOCK = "block";
const NONE = "none";

const SEARCH_OPTION_ID = "select-option";
const SEARCH_FILED_ID = "search-field";
const READING_PLACE_ID = "reading-place";

const TYPE = "type";
const EMAIL = "email";
const DATE = "date";
const TEXT = "text";

const ORDER_DATE = "orderDate";
const RETURNING_DATE = "returningDate";
const READING_PLACE = "readingPlace";



function changeSearchFieldType(){
    let selectOption = document.getElementById(SEARCH_OPTION_ID).value;
    let searchField = document.getElementById(SEARCH_FILED_ID);
    let readingPlaceField = document.getElementById(READING_PLACE_ID);
    switch (selectOption) {
        case EMAIL:
            searchField.setAttribute(TYPE, EMAIL);
            searchField.disabled = false;
            searchField.style.display = BLOCK;
            readingPlaceField.style.display = NONE;
            break;
        case ORDER_DATE:
            searchField.setAttribute(TYPE, DATE);
            searchField.disabled = false;
            searchField.style.display = BLOCK;
            readingPlaceField.style.display = NONE;
            break;
        case RETURNING_DATE:
            searchField.setAttribute(TYPE, DATE);
            searchField.disabled = false;
            searchField.style.display = BLOCK;
            readingPlaceField.style.display = NONE;
            break;
        case READING_PLACE:
            searchField.disabled = true;
            searchField.style.display = NONE;
            readingPlaceField.style.display = BLOCK;
            break;

        default:
            searchField.setAttribute(TYPE, TEXT);
            searchField.disabled = false;
            searchField.style.display = BLOCK;
            readingPlaceField.style.display = NONE;
    }
}