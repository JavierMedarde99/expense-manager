package com.bills.web.controller;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping({ "/", "" })
public class InnitWeb {

    private final WebService service;

    @GetMapping
    public String initWeb(Model model, HttpSession session,@AuthenticationPrincipal UserDetails authenticatedUser) {
        return service.dashboardMoth(model, session,authenticatedUser.getUsername());
    }

    @PostMapping("/year")
    public String postMethodName(Model model, HttpSession session) {

        return service.getYear(session, model);
    }

    @PostMapping("/month")
    public String getMethodName(Model model, HttpSession session, @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return service.moth(model, session, month, year);
    }

    @PostMapping("/deleteBill/{id}")
    public String deleteBillController(Model model, HttpSession session, @PathVariable Integer id,
            @RequestParam(required = false) String page, @RequestParam(defaultValue = "0") String amount) {
        return service.deleteBill(id, page, Integer.parseInt(amount), session, model);
    }

    @PostMapping("/updateBill/{id}")
    public String updateBillController(Model model, HttpSession session, @PathVariable Integer id,
            @RequestParam(required = false) String page, @RequestParam Integer amount, @RequestParam String name,
            @RequestParam Double price, @RequestParam String type, @RequestParam String subType,
            @RequestParam LocalDate dateBills) {
        return service.updateBill(model, session, id, page, amount, name, price, type, subType, dateBills);
    }

    @PostMapping("/insertBills")
    public String postMethodName(@RequestParam String name, @RequestParam Double price,
            @RequestParam String type, @RequestParam String subtype, @RequestParam LocalDate dateBills,
            @RequestParam Integer amount, HttpSession session, Model model) {

        return service.insertBills(name, price, type, subtype, dateBills, amount, session, model);
    }

}
