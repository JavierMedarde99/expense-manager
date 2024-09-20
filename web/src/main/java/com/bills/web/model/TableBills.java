package com.bills.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableBills {

    private String name;
    private String bill;
    private Double price;
    private Integer amount;

}
