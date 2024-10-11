package com.dev.petbackend.controller;

import com.dev.petbackend.exceptions.UserAlreadyExistsException;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.EntityConverter;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.dto.response.ApiResponse;
import com.dev.petbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody RegistrationRequest request) {
        try {
            User theUser = userService.add(request);
            UserDto registeredUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse("User registered successfully", registeredUser));

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
