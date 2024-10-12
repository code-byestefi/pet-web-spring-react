package com.dev.petbackend.controller;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.exceptions.UserAlreadyExistsException;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.EntityConverter;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.dto.request.UserUpdateRequest;
import com.dev.petbackend.model.dto.response.ApiResponse;
import com.dev.petbackend.services.user.UserService;
import com.dev.petbackend.utils.FeedBackMessage;
import com.dev.petbackend.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(UrlMapping.USERS)
public class UserController {

    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> add(@RequestBody RegistrationRequest request) {
        try {
            User theUser = userService.register(request);
            UserDto registeredUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS, registeredUser));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } catch(Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        try {
            User theUser = userService.update(userId, request);
            UserDto updatedUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_USER_BY_ID)
    public ResponseEntity<ApiResponse> getById(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId);
            UserDto deletedUser = entityConverter.mapEntityToDto(user, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.FOUND, deletedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
