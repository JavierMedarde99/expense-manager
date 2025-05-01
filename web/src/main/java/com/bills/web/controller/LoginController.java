package com.bills.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bills.web.services.LoginService;
import com.bills.web.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
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
    public String login(HttpServletRequest request, HttpSession session, Model model,
            @RequestParam(required = false) String username, @RequestParam(required = false) String password) {

        log.info("login call. QueryString {}", request.getQueryString());
        if (session.getAttribute(Constants.SUCCESS_PARAM) != null) {
            model.addAttribute(Constants.SUCCESS_PARAM, session.getAttribute(Constants.SUCCESS_PARAM).toString());
        }
        return "web/login";
    }

    @PostMapping("register")
    public String register(HttpServletRequest request, Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, @RequestParam(required = false) String password,
            @RequestParam(required = false) Double salary, HttpSession session) {

        log.info("register call. QueryString {}", request.getQueryString());
        return loginService.register(model, username, email, password, salary, session);
    }

    @PostMapping("deleteUser")
    public String deleteUserPage() {
        return "web/deleteUser";
    }

    @PostMapping("deleteUserConfirm")
    public String deleteUser(HttpSession session) {
        return loginService.deleteUser(session);
    }

    @PostMapping("/updateUser")
    public String updateUser(HttpServletRequest request, Model model, HttpSession session,
            @RequestParam(required = false) String username, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, @RequestParam(required = false) Double salary) {

        return loginService.updateUser(session, model, username, email, password, salary);
    }

    @ResponseBody
    @PostMapping("/checkUser")
    public ResponseEntity<String> checkUser(HttpServletRequest request, @RequestParam String username) {
        log.info("checkUser call. QueryString {}", request.getQueryString());
        return loginService.checkUser(username);
    }

    @PostMapping("/passwordForgot")
    public String postMethodName(HttpServletRequest request, Model model, @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        log.info("passwordForgot call. QueryString {}", request.getQueryString());
        return loginService.passwordForgot(model, username, password);
    }

}
