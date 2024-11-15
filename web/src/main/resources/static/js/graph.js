
const subType = ["videogames", "mtg", "book", "subsctiption", "peripherals", "clothes", "other"];

var months = document.getElementsByClassName("month");
var graph = document.getElementById("graph");
if (months != null) {
    months.forEach(month => {
        var element = document.createElement("canvas");
        document.body.insertBefore(element, graph);
        var bills_id = document.getElementsByClassName(month+"_id");
        bills_id.forEach(id =>{
            var price = document.getElementById(bills_id+"_price");
        })
    });
}

function generateGapth(){

}