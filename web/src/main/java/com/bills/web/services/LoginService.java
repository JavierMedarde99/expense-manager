package com.bills.web.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    public String register(Model model, String username, String email, String password, Double salary,
            HttpSession session) {
        if (username == null && password == null && email == null) {
            return "/web/register";
        }

        try {
            String pass = passwordEncoder.encode(password);
            Users user = new Users(username, email, pass, salary);
            usersRepository.save(user);
            session.setAttribute("success", "you have registered successfully");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("error to register the user. Error: {}", e.getMessage(), e);
            model.addAttribute("error", "That username is already in use");
            return "/web/register";
        }

    }

    public String convertSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
