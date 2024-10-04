package com.bills.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bills.web.entities.Users;

public interface UsersRepository extends CrudRepository<Users,Long>{
    
    @Query(value = "SELECT * FROM users WHERE (username =:username OR email =:email) AND password =:password", nativeQuery = true)
    Optional<Users> checkLogin(String username, String email, String password);
}
