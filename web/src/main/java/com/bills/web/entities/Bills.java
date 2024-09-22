package com.bills.web.entities;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.cglib.core.Local;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
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
