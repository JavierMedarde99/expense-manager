package com.bills.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.Bills;

public interface BillsRepository extends CrudRepository<Bills,Long>{
    
    @Query(value = "SELECT * FROM bills WHERE ((EXTRACT(MONTH FROM date_bills) =:month AND EXTRACT(YEAR FROM date_bills) =:year) or (type ='fixed' and EXTRACT(MONTH FROM date_bills) <=:monthFixed AND EXTRACT(YEAR FROM date_bills) <=:yearFixed))  AND user_id=:user", nativeQuery=true)
    List<Bills> getOneMonthBills(Integer month, Integer year, Integer user, Integer monthFixed, Integer yearFixed);

    @Query(value = "select EXTRACT(YEAR FROM b.date_bills)  from bills b where b.user_id = :id order by b.date_bills LIMIT 1", nativeQuery = true)
    Integer getLastYear(Long id);

}
