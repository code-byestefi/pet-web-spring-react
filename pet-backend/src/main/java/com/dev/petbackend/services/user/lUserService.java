package com.dev.petbackend.services.user;

import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.dto.request.UserUpdateRequest;

public interface lUserService {
    User register(RegistrationRequest request);

    User update(Long userId, UserUpdateRequest request);
}
