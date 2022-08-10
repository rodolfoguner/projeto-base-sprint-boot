package br.fai.models.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/not-found")
    public String getNotFoundPage() {
        return "common/not-found";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "common/access-denied";
    }
}
