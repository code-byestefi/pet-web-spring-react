package com.dev.petbackend.services.veterinarian;

import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.Veterinarian;
import com.dev.petbackend.model.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface lVeterinarianService {
    UserDto mapVeterinarianToUserDto(Veterinarian veterinarian);

    List<UserDto> getAllVeterinariansWithDetails();

    List<UserDto> findAvailableVetsForAppointment(String specialization, LocalDate date, LocalTime time);

    List<Veterinarian> getVeterinariansBySpecialization(String specialization);

    List<Veterinarian> getAvailableVeterinarians(String specialization, LocalDate date, LocalTime time);

    boolean isVetAvailable(Veterinarian veterinarian, LocalDate requestedDate, LocalTime requestedTime);

    boolean doesAppointmentOverLap(Appointment existingAppointment, LocalTime requestedStartTime, LocalTime requestedEndTime);
}
