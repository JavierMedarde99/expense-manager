package com.bills.web.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.entities.Bills;
import com.bills.web.entities.Users;
import com.bills.web.model.MonthArray;
import com.bills.web.model.YearArray;
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
    
    public String dashboardMoth(Model model, HttpSession session){
        //TODO: cambiarlo cuando cree el login
        session.setAttribute("user", 9);
        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        LocalDate today = LocalDate.now();
        Integer year = today.getYear();
        Integer month = today.getMonthValue()-1;
        if(today.getMonthValue() == 1){
            year = year-1;
        }
        beforeMonth(today.getMonthValue(),today.getYear(),user,model);
        beforeMonth(month,year,user,model);

        return "web/index";
    }

    public String moth(Model model,HttpSession session, Integer month, Integer year){
        Integer user = Integer.parseInt(session.getAttribute("user").toString());
        List<MonthArray> monthModel = new ArrayList();
        for(int i=1; Month.values().length>i;i++){
                monthModel.add(new MonthArray(String.valueOf(i), Month.of(i).name()));                
        }
        List<YearArray> arrayInteger = new ArrayList();
        for(int i=2023; Integer.parseInt(Year.now().toString())>i;i++){
            arrayInteger.add(new YearArray(String.valueOf(i)));
        }

        model.addAttribute("months", monthModel);
        model.addAttribute("years", arrayInteger);

        if(month == null && year == null){
            month = LocalDate.now().getMonthValue();
            year = LocalDate.now().getYear();
        } 

        beforeMonth(month, year, user, model);

        return "web/month";
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

    private void beforeMonth(Integer month, Integer year,Integer user,Model model){
        double total =0;
        Integer yearBefore = year;
        List<Bills> listBills = billsRepository.getOneMonthBills(month, year,user);
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
