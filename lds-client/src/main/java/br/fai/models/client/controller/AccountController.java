package br.fai.models.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "account/sign-up";
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

    @GetMapping("/password-recovery")
    public String getPasswordRecovery() {
        return "account/password-recovery";
    }
}