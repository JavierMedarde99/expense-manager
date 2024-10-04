package com.bills.web.services;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Users;
import com.bills.web.repository.UsersRepository;
import com.bills.web.utils.Constants;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final UsersRepository usersRepository;

    public String login(HttpSession session,Model model, String username,String password){
        if(username == null && password == null){
            return "/web/login";
        }

        Optional<Users> optUser = usersRepository.checkLogin(username, username, password);

        if(optUser.isPresent()){
            return Constants.REDIRECT + "/";
        }else{
            model.addAttribute("error", "no user found");
            return "/web/login";
        }
    }

    public String register(Model model, String username,String email,String password,Double salary){
        if(username == null && password == null && email==null){
            return "/web/register";
        }

        Users user = new Users(username,email,password,salary);
        usersRepository.save(user);
        return "/";

    }
}
