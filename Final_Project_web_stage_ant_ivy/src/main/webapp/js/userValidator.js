let nameRegex = /^([\w\s]{0,30})$/;
let emailRegex = /^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
let loginRegex = /^([\w]{0,10})$/;
let passwordRegex = /^([a-zA-Z0-9@*#]{4,10})$/;

function userFormValidation() {

    let userName = document.getElementById("user-name");
    let userLastName = document.getElementById('user-last-name');
    let email = document.getElementById("email");
    let login = document.getElementById("user-login");
    let password = document.getElementById("user-password");

    if(!nameRegex.test(userName.value.toLowerCase())){
        userName.style.border = "1px solid red";
        userName.value = "";
        return false;
    }

    if(!nameRegex.test(userLastName.value.toLowerCase())){
        userLastName.style.border = "1px solid red";
        userLastName.value = "";
        return false;
    }

    if(!emailRegex.test(email.value.toLowerCase())){
        email.style.border = "1px solid red";
        email.value = "";
        return false;
    }

    if(!loginRegex.test(login.value.toLowerCase())){
        login.style.border = "1px solid red";
        login.value = "";
        return false;
    }

    if(!passwordRegex.test(password.value.toLowerCase())){
        password.style.border = "1px solid red";
        password.value = "";
        return false;
    }
}