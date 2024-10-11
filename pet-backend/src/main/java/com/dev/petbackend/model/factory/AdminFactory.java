package com.dev.petbackend.model.factory;

import com.dev.petbackend.model.Admin;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.repositories.AdminRepository;
import com.dev.petbackend.services.user.mapper.UserAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminFactory {

    private final AdminRepository adminRepository;
    private final UserAttributeMapper mapper;

    public Admin createAdmin(RegistrationRequest request) {
        Admin admin = new Admin();
        mapper.setCommonAttributes(request, admin);
        return adminRepository.save(admin);
    }

}
