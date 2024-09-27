function getID(id, amount,name,page) {
    let urlForm = "/deleteBill/" + id;
    document.getElementById("formModal").action = urlForm;

    if(document.getElementById("pageCallBack") != null){
        document.getElementById("pageCallBack").remove();
    }

    if (document.getElementById("selectAmount") == null) {
        createSelect(amount,name,page);
    } else {
        document.getElementById("selectAmount").remove();
        createSelect(amount,name,page);
    }

}

function createSelect(amount,name,page) {

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

    if (amount == 1) {
        let text = document.createElement("p");
        text.setAttribute("id", "selectAmount");
        document.getElementById("modalBody").appendChild(text);
        let textElement = "You want to delete the expense with the name " + name;
        document.getElementById("selectAmount").insertAdjacentText("afterbegin", textElement);

    } else {

        let selectAmount = document.createElement("select");
        selectAmount.setAttribute("id", "selectAmount");
        selectAmount.setAttribute("name", "amount");
        document.getElementById("formModal").appendChild(selectAmount);

        for (let i = 1; i <= amount; i++) {
            let option = document.createElement("option");
            option.setAttribute("value", i);
            option.text = i;
            selectAmount.appendChild(option);
        }
    }

}

