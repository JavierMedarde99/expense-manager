package com.bills.web.services;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bills.web.entities.Users;
import com.bills.web.repository.UsersRepository;
import com.bills.web.utils.Constants;

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

        // if the user is not registered
        if (username == null && password == null && email == null) {
            return "web/register";
        }

        try {
            String pass = passwordEncoder.encode(password);
            Users user = new Users(username, email, pass, salary);
            usersRepository.save(user);
            session.setAttribute(Constants.SUCCESS_PARAM, "you have registered successfully");
            return Constants.REDIRECT+ "login";
        } catch (Exception e) {
            log.error("error to register the user. Error: {}", e.getMessage(), e);
            model.addAttribute("error", "That username is already in use");
            return "web/register";
        }

    }

    public String deleteUser(HttpSession session) {
        try {
            usersRepository.deleteById(Long.parseLong(session.getAttribute("user").toString()));
            session.setAttribute(Constants.SUCCESS_PARAM, "you have deleted the user successfully");
            return Constants.REDIRECT + "/logout";
        } catch (Exception e) {
            log.error("error to delete the user. Error: {}", e.getMessage(), e);
            return "web/login";
        }
    }

    public String updateUser(HttpSession session,Model model, String username, String email, String password, Double salary) {
        try {
            Users user = usersRepository.findById(Long.parseLong(session.getAttribute("user").toString())).get();
            model.addAttribute("user", user);

            if(username == null && password == null && email == null) {
                return "web/updateUser";
            }

            if (username != null) {
                user.setUserName(username);
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (password != null) {
                user.setPassword(passwordEncoder.encode(password));
            }
            if (salary != null) {
                user.setSalary(salary);
            }
            usersRepository.save(user);
            model.addAttribute(Constants.SUCCESS_PARAM, "you have updated the user successfully, please log again");
            return Constants.REDIRECT + "logout";
        } catch (Exception e) {
            log.error("error to update the user. Error: {}", e.getMessage(), e);
            model.addAttribute("error", "Error to update the user");
            return "web/updateUser";
        }
    }

    public ResponseEntity<String> checkUser(String username) {
        try {
            
            Optional<Users> optUser = usersRepository.findByUserName(username);
            if(optUser.isPresent()){
                return ResponseEntity.ok("true");
            }
            return ResponseEntity.internalServerError().body("false");
        } catch (Exception e) {
            log.error("error to check the user. Error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("false");
        }
    }

    public String passwordForgot(Model model, String username, String password) {
        try {

            if(username == null && password == null) {
                return "web/passwordForgot";
            }

            Users user = usersRepository.findByUserName(username).get();
            user.setPassword(passwordEncoder.encode(password));
            usersRepository.save(user);
            model.addAttribute(Constants.SUCCESS_PARAM, "you have updated the password successfully");
            return "web/login";
        } catch (Exception e) {
            log.error("error to update the password. Error: {}", e.getMessage(), e);
            model.addAttribute(Constants.ERROR_PARAM, "error to update the password");
            return "web/passwordForgot";
        }
    }
}
