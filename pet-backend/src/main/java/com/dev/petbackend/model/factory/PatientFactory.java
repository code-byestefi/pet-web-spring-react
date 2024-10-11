package com.dev.petbackend.model.factory;

import com.dev.petbackend.model.Patient;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.repositories.PatientRepository;
import com.dev.petbackend.services.user.mapper.UserAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientFactory {

    private final UserAttributeMapper mapper;
    private final PatientRepository patientRepository;

    public Patient createPatient(RegistrationRequest request) {
        Patient patient = new Patient();
        mapper.setCommonAttributes(request, patient);
        return patientRepository.save(patient);
    }
}
