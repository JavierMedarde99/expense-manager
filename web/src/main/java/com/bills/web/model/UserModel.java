package com.bills.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModel {
    
    private String username;
    private String password;
    private UserRole role;

    public enum UserRole {
        ADMIN, USER
    }
}
