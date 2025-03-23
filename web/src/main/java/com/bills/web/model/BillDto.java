package com.bills.web.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {
    Integer amount;
    String name;
    Double price; 
    String type; 
    String subType; 
    LocalDate dateBills;
}
