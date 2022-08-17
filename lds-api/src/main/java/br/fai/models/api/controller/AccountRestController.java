package br.fai.models.api.controller;

import br.fai.models.api.service.UserRestService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountRestController {

    @Autowired
    UserRestService userRestService;

    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestParam("username") final String username,
                                           @RequestParam("password") final String password) {

        UserModel userModel = userRestService.validateLogin(username, password);

        if (userModel == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userModel);
    }
}
