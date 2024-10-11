package com.dev.petbackend.controller;

import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User add(@RequestBody RegistrationRequest request) {
        return userService.add(request);
    }
}
