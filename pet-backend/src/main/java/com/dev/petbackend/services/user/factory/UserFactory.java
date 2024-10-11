package com.dev.petbackend.services.user.factory;

import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;

public interface UserFactory {
    public User createUser(RegistrationRequest request);
}
