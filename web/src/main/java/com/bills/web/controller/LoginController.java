package com.bills.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.services.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("login")
    public String login(Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        return loginService.login(model, username, password);
    }

    @GetMapping("register")
    public String register(Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            @RequestParam(required = false) Double salary) {
        return loginService.register(model, username, email, password, salary);
    }

}