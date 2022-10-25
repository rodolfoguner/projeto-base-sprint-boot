package br.fai.models.client.controller;

import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
        return "account/sign-in-page";
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

//    @PostMapping("/login")
//    public String login(@RequestParam("username") final String username,
//                        @RequestParam("password") final String password) {
//
//        UserModel userModel = userService.validateUsernameAndPassword(username, password);
//
//        if (userModel == null) {
//            return "redirect:/account/sign-up";
//        }
//
//        return "redirect:/";
//    }

    @GetMapping("/profile")
    public String getProfilePage(final Model model, final HttpSession session) {

        UserModel user = (UserModel) session.getAttribute("currentUser");

        if (user == null) {
            return "redirect:/user";
        }

        model.addAttribute("user", user);

        return "user/detail";
    }
}
