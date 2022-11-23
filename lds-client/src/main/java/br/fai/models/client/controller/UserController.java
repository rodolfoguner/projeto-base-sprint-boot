package br.fai.models.client.controller;

import br.fai.models.client.service.ReportService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService<UserModel> userService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String getUsers(final Model model, HttpSession session) {

        UserModel userModel = (UserModel) session.getAttribute("currentUser");

        model.addAttribute("user", userModel);

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
    public String delete(@PathVariable("id") final int id, final Model model, HttpSession session) {

        userService.deleteById(id);

        return getUsers(model, session);
    }

    @GetMapping("/report/read-all")
    public ResponseEntity<byte[]> generateResport() {

        String filePath = reportService.generateAndGetPdfFilePath();

        if (filePath.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        final File pdfFile = Paths.get(filePath).toFile();

        final byte[] fileContent;

        try {
            fileContent = Files.readAllBytes(pdfFile.toPath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();

        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String fileName = pdfFile.getName();
        // Esse header permite que o conteudo seja exibido no navegador
        headers.add("Content-Disposition", "inline; filename=" + fileName);

        headers.setCacheControl("must-revelidate, post-check-0, pre-check-0");

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
