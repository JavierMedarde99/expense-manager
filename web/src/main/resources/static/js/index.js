function getID(id, amount, name, page) {
    let urlForm = "/deleteBill/" + id;
    document.getElementById("formModal").action = urlForm;

    if (document.getElementById("pageCallBack") != null) {
        document.getElementById("pageCallBack").remove();
    }

    if (document.getElementById("selectAmount") == null) {
        createSelect(amount, name, page);
    } else {
        document.getElementById("selectAmount").remove();
        document.getElementById("selectText").remove();
        createSelect(amount, name, page);
    }
}

function getIDUpdate(id, amount, name, price, type, subtype, date, page) {
    let urlForm = "/updateBill/" + id;
    document.getElementById("formModalUpdate").action = urlForm;

    createInputHidden(page);
    let nameInput = document.getElementById("nameUpdate");
    nameInput.value = name;

    let priceInput = document.getElementById("priceUpdate");
    priceInput.value = price;

    let dateInput = document.getElementById("dateUpdate");
    dateInput.value = date;

    let amountInput = document.getElementById("amountUpdate");
    amountInput.value = amount;

}

function createSelect(amount, name, page) {

    createInputHidden(page)

    console.log(amount);

    if (amount == 1) {
        let text = document.createElement("p");
        text.setAttribute("id", "selectText");
        document.getElementById("text-delete").appendChild(text);
        let textElement = "You want to delete the expense with the name " + name;
        document.getElementById("selectText").insertAdjacentText("afterbegin", textElement);

    } else {

        let text = document.createElement("p");
        text.setAttribute("id", "selectText");
        document.getElementById("text-delete").appendChild(text);
        let textElement = "You want to delete the expense with the name " + name + ". Amount:";
        document.getElementById("selectText").insertAdjacentText("afterbegin", textElement);

        if (document.getElementById("selectAmount") != null) {
            document.getElementById("selectAmount").remove();
        }

        let selectAmount = document.createElement("select");
        selectAmount.setAttribute("id", "selectAmount");
        selectAmount.setAttribute("name", "amount");
        document.getElementById("text-delete").appendChild(selectAmount);

        for (let i = 1; i <= amount; i++) {
            let option = document.createElement("option");
            option.setAttribute("value", i);
            option.text = i;
            selectAmount.appendChild(option);
        }
    }

}

function createInputHidden(page) {
    let inputpage = document.createElement("input");
    inputpage.setAttribute("type", "hidden");
    inputpage.setAttribute("name", "page");
    if (page == null || page == undefined) {
        inputpage.setAttribute("value", "");
    } else {
        inputpage.setAttribute("value", page);
    }
    inputpage.setAttribute("id", "pageCallBack");
    document.getElementById("formModal").appendChild(inputpage);
}

const formElement = document.getElementById("form");

formElement.addEventListener("submit", function (event) {
    addSpinner(event);
})

function addSpinner(event) {
    let button = document.getElementById("button_form");
    button.hidden = true;
    let form = button.parentElement;
    let flexSpinner = document.createElement("div");
    flexSpinner.classList.add("d-flex");
    flexSpinner.classList.add("justify-content-center");
    let spinner = document.createElement("div");
    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    spinner.classList.add("text-black");
    spinner.setAttribute("role", "status");
    spinner.setAttribute("aria-hidden", "true");
    spinner.style.width = "1.5rem";
    spinner.style.height = "1.5rem";
    let span = document.createElement("span");
    span.classList.add("visually-hidden");
    span.innerText = "Loading...";
    flexSpinner.appendChild(spinner);
    spinner.appendChild(span);
    form.appendChild(flexSpinner);
}

function togglePassword() {
    let passwordInput = document.getElementById("password");
    let image = document.getElementById("eyePassword");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        image.src = "/images/eyeOpen.png";
    } else {
        passwordInput.type = "password";
        image.src = "/images/eyeClose.png";
    }
}