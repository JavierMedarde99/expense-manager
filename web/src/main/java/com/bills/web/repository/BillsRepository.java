package com.bills.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.Bills;

public interface BillsRepository extends CrudRepository<Bills,Long>{
    
    @Query(value = "SELECT * FROM bills WHERE MONTH(date_bills) =:month AND YEAR(date_bills) =:year", nativeQuery=true)
    List<Bills> getOneMonthBills(Integer month, Integer year);
}
