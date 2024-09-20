package com.bills.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Users {
    
    @Id
    @Generated
    private Long id;

    private String userName;
    private String email;
    private String password;

}
