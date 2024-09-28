package com.bills.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.RevenueMonth;
import com.bills.web.model.YearBills;

public interface RevenueMonthRepository extends CrudRepository<RevenueMonth,Long>{
    
    @Query(value = "SELECT revenue FROM revenue_month WHERE month=:month and year=:year", nativeQuery = true)
    Double getRevenue(Integer month,Integer year);

    @Query(value = "SELECT  sum(md.month_salary) total_earnings, SUM(md.total) total_bill_year, year,  sum(md.month_salary)-SUM(md.total) as money_saved FROM month_data md where md.id_user = :id GROUP by md.year", nativeQuery = true)
    List<YearBills> geyAllYear(Long id);
}
