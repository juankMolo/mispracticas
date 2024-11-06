// src/main/java/com/example/microusers/controller/UserController.java
package com.example.microusers.controller;

import com.example.microusers.model.User;
import com.example.microusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://192.168.100.3:8080", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint para autenticación de usuarios (inicio de sesión)
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user); // Retorna el usuario si la autenticación es exitosa
        } else {
            return ResponseEntity.status(401).build(); // Retorna un estado 401 si falla
        }
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Retorna la lista de usuarios
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser); // Retorna el usuario creado con estado 201
    }

    // Endpoint para actualizar un usuario existente
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser); // Retorna el usuario actualizado si existe
        } else {
            return ResponseEntity.notFound().build(); // Retorna un estado 404 si el usuario no fue encontrado
        }
    }

    // Endpoint para eliminar un usuario
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build(); // Retorna un estado 204 si el usuario fue eliminado exitosamente
        } else {
            return ResponseEntity.notFound().build(); // Retorna un estado 404 si el usuario no fue encontrado
        }
    }
}

