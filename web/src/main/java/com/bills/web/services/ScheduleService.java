package com.bills.web.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bills.web.entities.Bills;
import com.bills.web.entities.RevenueMonth;
import com.bills.web.entities.Users;
import com.bills.web.repository.BillsRepository;
import com.bills.web.repository.RevenueMonthRepository;
import com.bills.web.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final UsersRepository usersRepository;

    private final BillsRepository billsRepository;

    private final RevenueMonthRepository revenueMonthRepository;

    public void endOfMonth() {

        Iterable<Users> listUSer = usersRepository.findAll();
        
        Integer month = LocalDate.now().getMonthValue();
        Integer year = LocalDate.now().getYear();
        if (month == 1) {
            year = year - 1;
            month = 12;
        }else{
            month = month - 1;
        }

        for (Users users : listUSer) {
            Double total =getTotalBills(month, year, Integer.parseInt(users.getId().toString()));
            Double revenue = getRevenue(month, year, Integer.parseInt(users.getId().toString()), users.getSalary(), total);
            RevenueMonth montEntity = new RevenueMonth(month, year, revenue, users, total, users.getSalary());
            revenueMonthRepository.save(montEntity);

        }

    }

    private Double getTotalBills(Integer month,Integer year,Integer idUser){
        Double total =0.00;
        List<Bills> listBills = billsRepository.getOneMonthBills(month, year, idUser);
        for (Bills bills : listBills) {
            total = (bills.getPrice() * bills.getAmount()) + total;
        }
        return total;
    }

    private Double getRevenue(Integer month, Integer year, Integer idUser,Double salary, Double totalBills){

        Optional<Double> optRevenue = revenueMonthRepository.getRevenue(month, year,idUser);

        if(optRevenue.isPresent()){
            Double revenueBefore = optRevenue.get();
            return revenueBefore + salary - totalBills;
        }else{
            return salary - totalBills;
        }

    }
}
