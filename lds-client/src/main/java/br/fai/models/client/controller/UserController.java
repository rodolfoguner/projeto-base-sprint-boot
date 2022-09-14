package br.fai.models.client.controller;

import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService<UserModel> userService;

    @GetMapping("")
    public String getUsers(final Model model) {
        List<UserModel> users = userService.find();

        if(users == null || users.isEmpty()) {
            model.addAttribute("users", new ArrayList<UserModel>());
        } else {
            model.addAttribute("users", users);
        }

        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") final int id) {
        return "";
    }
}
