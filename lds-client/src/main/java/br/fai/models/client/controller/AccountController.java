package br.fai.models.client.controller;

import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService<UserModel> userService;

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

    @PostMapping("/create")
    public String create(UserModel user) {

        userService.create(user);

        return "redirect:/user";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") final String username,
                        @RequestParam("password") final String password) {

        UserModel userModel = userService.validateUsernameAndPassword(username, password);

        if (userModel == null) {
            return "redirect:/account/sign-up";
        }

        return "redirect:/";
    }
}
