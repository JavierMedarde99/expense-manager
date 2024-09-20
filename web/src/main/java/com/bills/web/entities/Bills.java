package com.bills.web.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Bills {
    
    @Id
    @Generated
    private Long id;

    private String name;
    private Double price;
    private String type;
    private String subType;
    private LocalDate dateBills;
    private Integer amount;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name= "id")
    private Set<Users> idUser;
}
