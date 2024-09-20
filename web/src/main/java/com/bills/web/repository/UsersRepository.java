package com.bills.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.Users;

public interface UsersRepository extends CrudRepository<Users,Long>{
    
}
