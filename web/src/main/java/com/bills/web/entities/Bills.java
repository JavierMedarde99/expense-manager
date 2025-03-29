package com.bills.web.entities;

import java.time.LocalDate;

import com.bills.web.model.BillDto;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users idUser;

    public Bills(BillDto billDto, Users idUser){
        this.name = billDto.getName();
        this.price = billDto.getPrice();
        this.type = billDto.getType();
        this.subType = billDto.getSubType();
        this.dateBills = billDto.getDateBills();
        this.amount = billDto.getAmount();
        this.idUser = idUser;
    }
}
