
window.onload = function(){
    let today = new Date().toISOString().substr(0, 10);
    let orderDate = document.getElementById("order-date").value;
    if (!Date.parse(orderDate)) {
        document.querySelector("#order-date").value = today;
    }
};

function orderFormValidation() {
    let orderDate = document.getElementById("order-date").value;
    let returningDate = document.getElementById("returning-date");

    if (returningDate.value < orderDate) {
        let message = document.getElementById("returning-date-message");
        returningDate.style.border = "1px solid red";
        returningDate.value = orderDate;
        message.style.display = "block";
        message.style.color = "Red";
        return false;
    }

    let message = document.getElementById("returned-message").value;
    let returned = document.forms["administration-update-order"]["book_returned"].value;
    if (returned === "true") {
        return (confirm(message));
    }
}
