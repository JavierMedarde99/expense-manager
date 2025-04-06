const checkButton = document.getElementById("check");

checkButton.addEventListener("click", function (event) {
    event.preventDefault();
    let url;
    let domain = window.location.host;
    let userName = document.getElementById("usernameCheck").value;
    if(domain.includes("localhost")){
        url = "http://" + domain + "/api/user/check?username=" + userName;
    }else{
        url = "https://" + domain + "/api/user/check?username=" + userName;
    }
    fetch(url, {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            showForm(userName);
        } else {
            document.getElementById("error").innerHTML = "User does not exist";
            document.getElementById("error").hidden = false;
        }
    })
    
});

function showForm(username){
    document.getElementById("username").value = username;
    document.getElementById("formCheck").hidden = true;
    document.getElementById("form").hidden = false;

}