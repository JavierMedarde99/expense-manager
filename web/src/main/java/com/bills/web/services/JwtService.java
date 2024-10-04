package com.bills.web.services;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.bills.web.entities.Users;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    
    public generateJwt(Users users){
        return getToken(new HashMap<>(),users)
    }

    private String getToken(HashMap hashMap,Users users){
        return Jwts
        .builder()
        .claims()
        .subject(users.getId().toString())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+1000*60*24))
        
    }
}
