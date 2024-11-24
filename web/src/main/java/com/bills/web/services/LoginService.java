package com.bills.web.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Users;
import com.bills.web.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;

    public String register(Model model, String username, String email, String password, Double salary,HttpSession session) {
        if (username == null && password == null && email == null) {
            return "/web/register";
        }

        try {
            Users user = new Users(username, email, password, salary);
            usersRepository.save(user);
            session.setAttribute("success", "you have registered successfully");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("error to register the user. Error: {}", e.getMessage(), e);
            model.addAttribute("error", "That username is already in use");
            return "/web/register";
        }

    }
}
