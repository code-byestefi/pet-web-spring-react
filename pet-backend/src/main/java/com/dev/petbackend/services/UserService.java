package com.dev.petbackend.services;

import com.dev.petbackend.model.User;
import com.dev.petbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void add(User user) {
        userRepository.save(user);
    }
}
