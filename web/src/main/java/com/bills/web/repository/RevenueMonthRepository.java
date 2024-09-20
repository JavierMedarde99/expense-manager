package com.bills.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.RevenueMonth;

public interface RevenueMonthRepository extends CrudRepository<RevenueMonth,Long>{
    
    @Query(value = "SELECT revenue FROM revenue_month WHERE month=:month and year=:year", nativeQuery = true)
    Double getRevenue(Integer month,Integer year);
}
