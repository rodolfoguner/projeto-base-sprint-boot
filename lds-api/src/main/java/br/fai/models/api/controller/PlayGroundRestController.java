package br.fai.models.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playground")
@CrossOrigin(origins = "*")
public class PlayGroundRestController {

    @GetMapping("/test")
    public ResponseEntity<String> getPlayGroundData() {
        return ResponseEntity.ok("Boa noite. =D");
    }

    @GetMapping("/openapi-test")
    public ResponseEntity<String> getOpenApiTeste() {
        return ResponseEntity.ok("Teste com o OpenApi");
    }
}
