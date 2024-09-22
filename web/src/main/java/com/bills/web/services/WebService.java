package com.bills.web.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.entities.Bills;
import com.bills.web.entities.Users;
import com.bills.web.repository.BillsRepository;
import com.bills.web.repository.RevenueMonthRepository;
import com.bills.web.repository.UsersRepository;
import com.bills.web.utils.Constants;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebService {

    private final BillsRepository billsRepository;

    private final UsersRepository usersRepository;

    private final RevenueMonthRepository revenueMonthRepository;
    
    public String actualMoth(Model model, HttpSession session){
        LocalDate today = LocalDate.now();
        Integer year = today.getYear();
        Integer month = today.getMonthValue()-1;
        if(today.getMonthValue() == 1){
            year = year-1;
        }
        beforeMonth(today.getMonthValue(),today.getYear(),model);
        beforeMonth(month,year,model);

        //TODO: cambiarlo cuando cree el login
        session.setAttribute("user", 9);

        return "web/index";
    }

    public String insertBills(String name,Double price,
    String type, String subtype, LocalDate dateBills, Integer amount, HttpSession session, Model model ){
        Long idUser = Long.parseLong(session.getAttribute("user").toString()); 
        Optional<Users> optUsers = usersRepository.findById(idUser);
        if(optUsers.isPresent()){
            Users user = optUsers.get();
            Bills bill = new Bills(name, price, type, subtype, dateBills, amount, user.getId());
            billsRepository.save(bill);
            model.addAttribute("success", "save find bill");
        }else{
            model.addAttribute("error", "user not found in data base");
        }
        return Constants.REDIRECT + "/";
    }

    private void beforeMonth(Integer month, Integer year,Model model){
        double total =0;
        Integer yearBefore = year;
        List<Bills> listBills = billsRepository.getOneMonthBills(month, year);
        Integer mothBefore = month -1;
        for (Bills bill : listBills) {
            total =  total +(bill.getPrice() * bill.getAmount());
        }
        if(mothBefore == 1){
            yearBefore = year-1;
        }
        Double revenuaLastMoth = revenueMonthRepository.getRevenue(mothBefore, yearBefore);
        if(!month.equals(LocalDate.now().getMonthValue())){
            model.addAttribute("billsBefore", listBills);
            model.addAttribute("totalBefore", total);
            model.addAttribute("monthBefore", Month.of(month));
            model.addAttribute("amountBillsBefore", listBills.size()+2);
            model.addAttribute("revenueBefore", revenuaLastMoth+Constants.SALARY-total);
        }else{
            model.addAttribute("bills", listBills);
            model.addAttribute("total", total);
            model.addAttribute("month", LocalDate.now().getMonth().toString());
            model.addAttribute("amountBills", listBills.size()+2);
            model.addAttribute("revenue", revenuaLastMoth+Constants.SALARY-total);
        }
        
    }
}
