package com.bills.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "month_data")
public class RevenueMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;
    private Integer year;
    private Double revenue;
    private Long idUser;
    private Double total;
    private Double monthSalary;

    public RevenueMonth(Integer month,Integer year,Double revenue,Long idUser,Double total,Double monthSalary){
        this.month = month;
        this.year = year;
        this.revenue = revenue;
        this.idUser = idUser;
        this.total = total;
        this.monthSalary = monthSalary;
    }

}
