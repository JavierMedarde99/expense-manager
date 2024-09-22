package com.bills.web.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.services.WebService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping({"/",""})
public class InnitWeb {
    
    private final WebService service;

    @GetMapping
    public String initWeb(Model model,HttpSession session){
        return service.actualMoth(model,session);
    }

    @PostMapping("/insertBills")
    public String postMethodName(@RequestParam String name,@RequestParam Double price,
    @RequestParam String type, @RequestParam String subtype, @RequestParam LocalDate dateBills, @RequestParam Integer amount, HttpSession session
    , Model model) {
        
        
        return service.insertBills(name, price, type, subtype, dateBills, amount, session, model);
    }
    
    
}
