package br.fai.models.api.controller;

import br.fai.models.api.service.UserRestService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    UserRestService<UserModel> userRestService;

    @GetMapping("")
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> users = userRestService.find();

        if (users == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable int id) {

        UserModel user = userRestService.findById(id);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<Integer> create(@RequestBody final UserModel userModel) {

        int userId = userRestService.create(userModel);

        if (userId == -1) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {
        boolean result = userRestService.deleteById(id);

        return !result ? ResponseEntity.badRequest().build() : ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") final int id,
                                          @RequestBody final UserModel entity) {
        boolean response = userRestService.update(id, entity);

        return response ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();

    }
}
