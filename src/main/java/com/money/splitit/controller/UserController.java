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

    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        this.userService.createUser(user);
        return Mono.just(ResponseEntity.ok(user));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
          return Mono.just(ResponseEntity.ok(user));
        }
        return Mono.just(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<User>>> getAllUsers() {
        return Mono.just(ResponseEntity.ok(userService.getAllUsers()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return Mono.just(ResponseEntity.ok(userService.updateUser(user)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Mono.just(ResponseEntity.ok().build());
    }
}