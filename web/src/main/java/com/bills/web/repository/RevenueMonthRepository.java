package com.bills.web.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.RevenueMonth;

public interface RevenueMonthRepository extends CrudRepository<RevenueMonth,Long>{
    
    @Query(value = "SELECT revenue FROM month_data WHERE month=:month and year=:year and user_id=:id", nativeQuery = true)
    Optional<Double> getRevenue(Integer month,Integer year,Integer id);

    @Query(value = "SELECT year year_value,ROUND(sum(md.month_salary)::numeric,2) total_earnings, ROUND(SUM(md.total)::numeric,2) total_bill_year,  ROUND((sum(md.month_salary)-SUM(md.total))::numeric,2) as money_saved FROM month_data md where md.user_id = :id GROUP by md.year", nativeQuery = true)
    List<Map<String,Object>> geyAllYear(Long id);
}
