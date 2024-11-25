package com.bills.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.services.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("login")
    public String login(HttpSession session, Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        if (session.getAttribute("success") != null) {
            model.addAttribute("success", session.getAttribute("success").toString());
        }
        return "web/login";
    }

    @PostMapping("register")
    public String register(Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            @RequestParam(required = false) Double salary, HttpSession session) {
                log.info("register : {}",username);
        return loginService.register(model, username, email, password, salary, session);
    }

}
