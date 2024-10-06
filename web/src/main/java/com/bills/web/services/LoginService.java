package com.bills.web.services;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Users;
import com.bills.web.repository.UsersRepository;
import com.bills.web.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(HttpSession session,Model model, String username,String password){
        if(username == null && password == null){
            return "/web/login";
        }

        Optional<Users> optUser = usersRepository.checkLogin(username, username, password);

        if(optUser.isPresent()){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails user=optUser.get();
            String token=jwtService.getToken(user);
            response.addHeader("bar", token);
            return "/web/index";
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
