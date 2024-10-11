package com.dev.petbackend.model.factory;

import com.dev.petbackend.model.Veterinarian;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.repositories.VeterinarianRepository;
import com.dev.petbackend.services.user.mapper.UserAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeterinarianFactory {

    private final VeterinarianRepository veterinarianRepository;
    private final UserAttributeMapper userAttributeMapper;

    public Veterinarian createVeterinarian(RegistrationRequest request) {

        Veterinarian veterinarian = new Veterinarian();
        userAttributeMapper.setCommonAttributes(
                request, veterinarian
        );
        veterinarian.setSpecialization(request.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }
}
