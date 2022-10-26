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
    UserRestService<UserModel> userRestService;

    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestHeader("Authorization") final String encodedData) {

        UserModel userModel = userRestService.validateLogin(encodedData);

        if (userModel == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userModel);
    }
}
