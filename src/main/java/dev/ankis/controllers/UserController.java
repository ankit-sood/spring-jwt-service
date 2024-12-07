package dev.ankis.controllers;

import dev.ankis.entities.User;
import dev.ankis.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }
}
