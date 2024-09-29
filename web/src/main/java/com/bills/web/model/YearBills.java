package com.bills.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YearBills {
    private Integer yearValue;
    private Double totalBillYear;
    private Double totalEarnings;
    private Double moneySaved;
}
