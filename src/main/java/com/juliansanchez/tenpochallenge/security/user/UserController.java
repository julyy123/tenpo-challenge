package com.juliansanchez.tenpochallenge.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(final @RequestBody UserDto userDto){
        userService.register(userDto);

        return ResponseEntity.ok(URI.create("/login"));
    }
}
