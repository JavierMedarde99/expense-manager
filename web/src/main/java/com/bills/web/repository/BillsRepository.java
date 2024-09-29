package com.bills.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.Bills;

public interface BillsRepository extends CrudRepository<Bills,Long>{
    
    @Query(value = "SELECT * FROM bills WHERE ((MONTH(date_bills) =:month AND YEAR(date_bills) =:year) or date_bills is null)  AND id_user=:user", nativeQuery=true)
    List<Bills> getOneMonthBills(Integer month, Integer year, Integer user);

    @Query(value = "select year(b.date_bills)  from bills b where b.id_user = :id order by b.date_bills LIMIT 1", nativeQuery = true)
    Integer getLastYear(Long id);

}
