package com.bills.web.entities;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Bills {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String type;
    private String subType;
    @Nullable
    private LocalDate dateBills;
    private Integer amount;
    private Long idUser;



    public Bills(String name, Double price, String type, String subType, LocalDate dateBills,
    Integer amount, Long idUser){

        this.name = name;
        this.price= price;
        this.type = type;
        this.subType = subType;
        this.dateBills = dateBills;
        this.amount = amount;
        this.idUser = idUser;
    }
}
