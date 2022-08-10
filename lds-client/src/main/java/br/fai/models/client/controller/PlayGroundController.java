package br.fai.models.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayGroundController {

    @GetMapping("/playground/test")
    public String getTestPage() {
        return "playground/test-page";
    }
}
