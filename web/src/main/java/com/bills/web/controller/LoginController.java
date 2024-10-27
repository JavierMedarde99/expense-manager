package com.bills.web.controller;

import java.net.http.HttpResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bills.web.services.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("login")
    public String login(HttpSession session, Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        return "web/login";
    }

    @PostMapping("login")
    public String loginCehck(HttpSession session,HttpServletRequest request,HttpServletResponse response ,Model model, @RequestParam String username,
            @RequestParam  String password) {
        return loginService.login(session,model, username, password);
    }
    

    @GetMapping("register")
    public String register(Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            @RequestParam(required = false) Double salary) {
        return loginService.register(model, username, email, password, salary);
    }

}
