// src/main/java/com/example/frontend/controller/ViewController.java
package com.example.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Ruta para renderizar el template index.html (página de inicio de sesión)
    @GetMapping("/")
    public String index() {
        return "index"; // Renderiza templates/index.html
    }

    // Ruta para renderizar el template users.html (página de gestión de usuarios)
    @GetMapping("/users")
    public String users() {
        return "users"; // Renderiza templates/users.html
    }
}

