let ascendingOrder = [true, true];

function sortTable(columnIndex) {

    const table = document.getElementById("tableSort");
    const bodyTable = table.tBodies[0];
    const rows = Array.from(bodyTable.rows);

    const fixedRow = rows[1]; // fixed data on the left
    const totalRow = rows[rows.length - 1]; //total row

    const orderRows = rows
    .filter(item => item.i !== 1 && item.i !== 0 && item.i !== rows.length - 1);

    const asc = ascendingOrder[columnIndex];
    ascendingOrder[columnIndex] = !asc;

        orderRows.sort((a, b) => {

            let valA = a.cells[columnIndex].textContent.trim();
            let valB = b.cells[columnIndex].textContent.trim();

            // Detect if it is a number
            const isNumber = !isNaN(valA) && !isNaN(valB);
            if (isNumber) {
                valA = parseFloat(valA);
                valB = parseFloat(valB);
            } else {
                valA = valA.toLowerCase();
                valB = valB.toLowerCase();
            }

            if (valA < valB) return asc ? -1 : 1;
            if (valA > valB) return asc ? 1 : -1;
            return a.i - b.i;
        });
    
    // Limpiar el tbody
    bodyTable.innerHTML = '';

    // Insertar filas: la primera, la fija, luego las ordenadas
    bodyTable.appendChild(rows[0]); // Primera fila (posiblemente encabezado de sección)
    bodyTable.appendChild(fixedRow); // Fila que queremos mantener en su lugar
    orderRows.forEach(row => bodyTable.appendChild(row));
    bodyTable.appendChild(totalRow)
}
