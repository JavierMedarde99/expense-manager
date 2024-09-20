package com.bills.web.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Bills;
import com.bills.web.repository.BillsRepository;
import com.bills.web.repository.RevenueMonthRepository;
import com.bills.web.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebService {

    private final BillsRepository billsRepository;

    private final RevenueMonthRepository revenueMonthRepository;
    
    public String actualMoth(Model model){
        double total =0;
        LocalDate today = LocalDate.now();
        Integer year = today.getYear();
        Integer month = today.getMonthValue()-1;
        List<Bills> listBills = billsRepository.getOneMonthBills(today.getMonthValue(), today.getYear());
        for (Bills bill : listBills) {
            total =  bill.getPrice() * bill.getAmount();
        }
        if(today.getMonthValue() == 1){
            year = year-1;
        }
        Double revenuaLastMoth = revenueMonthRepository.getRevenue(month, year);
        model.addAttribute("bills", listBills);
        model.addAttribute("total", total);
        model.addAttribute("month", today.getMonth().toString());
        model.addAttribute("amountBills", listBills.size()+2);
        model.addAttribute("revenue", revenuaLastMoth+Constants.SALARY-total);
        return "web/index";
    }
}
