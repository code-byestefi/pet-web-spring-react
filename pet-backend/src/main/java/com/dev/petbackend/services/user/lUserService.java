package com.dev.petbackend.services.user;

import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.dto.request.UserUpdateRequest;

import java.sql.SQLException;
import java.util.List;

public interface lUserService {
    User register(RegistrationRequest request);

    User update(Long userId, UserUpdateRequest request);

    User findById(Long userId);

    void delete(Long userId);

    List<UserDto> getAllUsers();

    UserDto getUserWithDetails(Long userId) throws SQLException;
}
