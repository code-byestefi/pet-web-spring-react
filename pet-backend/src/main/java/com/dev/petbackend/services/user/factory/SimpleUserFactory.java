package com.dev.petbackend.services.user.factory;

import com.dev.petbackend.exceptions.UserAlreadyExistsException;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.factory.AdminFactory;
import com.dev.petbackend.model.factory.PatientFactory;
import com.dev.petbackend.model.factory.VeterinarianFactory;
import com.dev.petbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {

    private final UserRepository userRepository;

    private final VeterinarianFactory veterinarianFactory;
    private final PatientFactory patientFactory;
    private final AdminFactory adminFactory;

    @Override
    public User createUser(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Oops! " + request.getEmail() + " already exists!");
        }
        switch ( request.getUserType()) {
            case "VET" -> {return veterinarianFactory.createVeterinarian(request);
            }
            case "PATIENT" -> {return patientFactory.createPatient(request);
            }
            case "ADMIN" -> { return adminFactory.createAdmin(request);
            }
            default -> {
                return null;
            }
        }
    }
}
