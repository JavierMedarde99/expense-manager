
let months = document.getElementsByClassName("month");
let graph = document.getElementById("graph");
let cont = 0;
if (months != null) {
    for (let month of months) {
        let subType = {
            videogames: 0,
            mtg: 0,
            book: 0,
            subsctiption: 0,
            peripherals: 0,
            clothes: 0,
            other: 0,
        }
        let element = document.createElement("canvas");
        let div = document.createElement("div");
        let titel = document.createElement("h2");
        titel.innerHTML = month.innerHTML;
        titel.style.color = "white";
        titel.style.textAlign = "center";
        if(cont == 0){
            div.className = "graph";
            div.style.top = "40%";
        }else{
            div.className = "graph2";
            let firstDiv = document.getElementsByClassName("graph")[0];
            firstDiv.style.top = "25%";
        }
        cont++;
        graph.appendChild(div);
        div.appendChild(titel)
        div.appendChild(element);
        let bills_id = document.getElementsByClassName(month.innerHTML + "_id");
        for (let id of bills_id) {
            let price = document.getElementById(month.innerHTML + "_" + id.innerHTML + "_price");
            let amount = document.getElementById(month.innerHTML + "_" + id.innerHTML + "_amount");
            let type = document.getElementById(month.innerHTML + "_" + id.innerHTML + "_type");
            console.log(price.innerHTML);
            let result_bill = price.innerHTML * amount.innerHTML;
            switch (type.innerHTML) {
                case "videogames":
                    subType.videogames = subType.videogames + result_bill;
                    break;
                case "mtg":
                    subType.mtg = subType.mtg + result_bill;
                    break;
                case "book":
                    subType.book = subType.book + result_bill;
                    break;
                case "subsctiption":
                    subType.subsctiption = subType.subsctiption + result_bill;
                    break;
                case "peripherals":
                    subType.peripherals = subType.peripherals + result_bill;
                    break;
                case "clothes":
                    subType.clothes = subType.clothes + result_bill;
                    break;
                default:
                    subType.other = subType.other + result_bill;
                    break;
            }
        }
        let total = document.getElementById(month.innerHTML+"_total");
        total = parseInt(total.innerHTML);
        subType.videogames = ((subType.videogames / total )*100).toFixed(2);
        subType.mtg = ((subType.mtg / total )*100).toFixed(2);
        subType.book = ((subType.book / total )*100).toFixed(2);
        subType.subsctiption = ((subType.subsctiption / total )*100).toFixed(2);
        subType.peripherals = ((subType.peripherals / total )*100).toFixed(2);
        subType.clothes = ((subType.clothes / total )*100).toFixed(2);
        subType.other = ((subType.other / total )*100).toFixed(2);
        generateGapth(Object.keys(subType),Object.values(subType),month.innerHTML,element);
    }

}


function generateGapth(arrayKeys,arrayValues,month,element) {
    console.log(arrayValues);
        // Crea un nuevo objeto Chart con opciones
        var chart = new Chart(element, {
            // Configuración del tipo de gráfico
            type: 'doughnut',
    
            // Datos del gráfico
            data: {
                labels: arrayKeys,
                datasets: [{
                    label: month,
                    data: arrayValues,
                    backgroundColor: [
                        'rgb(137, 0, 79)',
                        'rgb(127, 0, 0)',
                        'rgb(133, 52, 0)',
                        'rgb(135, 146, 0)',
                        'rgb(0, 52, 0)',
                        'rgb(0, 55, 133)',
                        'rgb(27, 0, 75)'
                    ],
                    hoverOffset: 7
                }]
            }
        });
}