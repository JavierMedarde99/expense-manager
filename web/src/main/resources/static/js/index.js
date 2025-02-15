function getID(id, amount,name,page) {
    let urlForm = "/deleteBill/" + id;
    document.getElementById("formModal").action = urlForm;

    if(document.getElementById("pageCallBack") != null){
        document.getElementById("pageCallBack").remove();
    }

    if (document.getElementById("selectAmount") == null) {
        createSelect(amount,name,page);
    }else{
        document.getElementById("selectAmount").remove();
        document.getElementById("selectText").remove();
        createSelect(amount,name,page);
    }
}

function getIDUpdate(id, amount,name,price,type,subtype,date,page){
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

function createSelect(amount,name,page) {

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
        let textElement = "You want to delete the expense with the name " + name+". Amount:";
        document.getElementById("selectText").insertAdjacentText("afterbegin", textElement);

        if(document.getElementById("selectAmount") != null){
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

function createInputHidden(page){
    let inputpage = document.createElement("input");
    inputpage.setAttribute("type", "hidden");
    inputpage.setAttribute("name", "page");
    if(page == null || page == undefined){
        inputpage.setAttribute("value", "");
    }else{
        inputpage.setAttribute("value", page);
    }
    inputpage.setAttribute("id", "pageCallBack");
    document.getElementById("formModal").appendChild(inputpage);
}