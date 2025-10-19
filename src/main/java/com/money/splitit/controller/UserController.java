package com.money.splitit.controller;

import com.money.splitit.model.User;
import com.money.splitit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/profile")
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        System.out.println("Received user: " + user.getUsername());
        if (user.getGroups() != null) {
            user.getGroups().forEach(group ->
                    System.out.println("Group: " + group.getName())
            );
        }
        return Mono.just(ResponseEntity.ok(user));
    }
    @GetMapping("/profile")
    public Mono<ResponseEntity<String>> getProfile() {
        return Mono.just(ResponseEntity.ok("User profile"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /*@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}