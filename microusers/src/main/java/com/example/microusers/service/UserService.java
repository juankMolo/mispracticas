// src/main/java/com/example/microusers/service/UserService.java
package com.example.microusers.service;

import com.example.microusers.model.User;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private AtomicLong counter = new AtomicLong(1);

    public UserService() {
        // Inicialmente, la lista de usuarios está vacía para activar el patrón Retry
        // users.add(new User(counter.getAndIncrement(), "Juan Moreno", "juan@example.com", "juan", "juan123"));
        // users.add(new User(counter.getAndIncrement(), "Maria Gomez", "maria@example.com", "maria", "password456"));
    }

    public User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Profile("chaos")
    @Retry(name = "userService", fallbackMethod = "fallbackGetAllUsers")
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios en la lista. Reintentando...");
            throw new RuntimeException("No hay usuarios en la lista.");
        }

        System.out.println("Lista de usuarios recuperada exitosamente.");
        return users;
    }

    private List<User> fallbackGetAllUsers(Throwable throwable) {
        System.out.println("Se ha alcanzado el máximo de intentos para obtener usuarios. Ejecutando método de respaldo.");
        return new ArrayList<>(); // Devuelve una lista vacía o alguna otra acción alternativa
    }

    public User createUser(User user) {
        user.setId(counter.getAndIncrement());
        users.add(user);
        return user;
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            return user;
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(u -> u.getId().equals(id));
    }

    private User getUserById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}

