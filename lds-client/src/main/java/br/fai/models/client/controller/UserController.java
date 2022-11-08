package br.fai.models.client.controller;

import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService<UserModel> userService;

    @GetMapping("/")
    public String getUsers(final Model model) {
        List<UserModel> users = userService.find();

        if (users == null || users.isEmpty()) {
            model.addAttribute("users", new ArrayList<UserModel>());
        } else {
            model.addAttribute("users", users);
        }

        return "user/list";
    }

    @GetMapping("/detail/{id}")
    public String getDetailPage(@PathVariable("id") final int id, final Model model) {

        UserModel user = userService.findById(id);

        if (user == null) {
            return "redirect:/user";
        }

        model.addAttribute("user", user);

        return "user/detail";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") final int id, final Model model) {

        UserModel user = userService.findById(id);

        if (user == null) {
            return "redirect:/user";
        }

        model.addAttribute("user", user);

        return "user/edit";
    }

    @PostMapping("/update")
    public String update(final UserModel userModel, final Model model) {
        userService.update(userModel.getId(), userModel);

        return getDetailPage(userModel.getId(), model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") final int id, final Model model) {

        userService.deleteById(id);

        return getUsers(model);
    }

    @GetMapping("/report/read-all")
    public ResponseEntity<byte[]> generateResport() {

        return null;
    }
}
