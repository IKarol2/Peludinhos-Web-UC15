package br.com.peludinhos.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Abre direto o index.html quando acessar http://localhost:8080/
    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }
}
