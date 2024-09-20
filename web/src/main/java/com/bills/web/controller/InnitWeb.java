package com.bills.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bills.web.services.WebService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/",""})
public class InnitWeb {
    
    private final WebService service;

    @GetMapping
    public String initWeb(Model model){
        return service.actualMoth(model);
    }

    
}
