package com.dev.petbackend.services.user;

import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.services.user.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User add(RegistrationRequest request) {
        return userFactory.createUser(request);
    }
}
