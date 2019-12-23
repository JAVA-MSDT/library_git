const DISPLAY_OPTION = "displayOption";
const DISPLAY_CARD = "displayCard";
const DISPLAY_TABLE = "displayTable";

const DISPLAY_OPTION_ID = "display-option";
const DISPLAY_CARD_ID = "display-book-card";
const DISPLAY_TABLE_ID = "display-book-table";

const BLOCK = "block";
const NONE = "none";


window.onload = function () {
    let displayOption = document.getElementById(DISPLAY_OPTION_ID);
    let displayCardDiv = document.getElementById(DISPLAY_CARD_ID);
    let displayTableDiv = document.getElementById(DISPLAY_TABLE_ID);

    displayOption.value = localStorage.getItem(DISPLAY_OPTION);
    displayCardDiv.style.display = localStorage.getItem(DISPLAY_CARD);
    displayTableDiv.style.display = localStorage.getItem(DISPLAY_TABLE);

};


function displayBook() {
    let displayOption = document.getElementById(DISPLAY_OPTION_ID).value;
    let displayCardDiv = document.getElementById(DISPLAY_CARD_ID);
    let displayTableDiv = document.getElementById(DISPLAY_TABLE_ID);

    switch (displayOption) {
        case DISPLAY_CARD:
            displayCardDiv.style.display = BLOCK;
            displayTableDiv.style.display = NONE;

            localStorage.setItem(DISPLAY_OPTION, DISPLAY_CARD);
            localStorage.setItem(DISPLAY_CARD, BLOCK);
            localStorage.setItem(DISPLAY_TABLE, NONE);
            break;

        case DISPLAY_TABLE:
            displayCardDiv.style.display = NONE;
            displayTableDiv.style.display = BLOCK;

            localStorage.setItem(DISPLAY_OPTION, DISPLAY_TABLE);
            localStorage.setItem(DISPLAY_CARD, NONE);
            localStorage.setItem(DISPLAY_TABLE, BLOCK);
            break;

    }
}