package com.bills.web.entities;

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
public class RevenueMonth {
    @Id
    @Generated
    private Long id;

    private Integer month;
    private Integer year;
    private Double revenue;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name= "id")
    private Set<Users> idUser;
}
