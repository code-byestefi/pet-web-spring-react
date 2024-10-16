package com.dev.petbackend.services.veterinarian;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.Veterinarian;
import com.dev.petbackend.model.dto.EntityConverter;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.repositories.AppointmentRepository;
import com.dev.petbackend.repositories.ReviewRepository;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.repositories.VeterinarianRepository;
import com.dev.petbackend.services.photo.lImageService;
import com.dev.petbackend.services.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarianService implements lVeterinarianService{

    private final VeterinarianRepository veterinarianRepository;
    private final EntityConverter<Veterinarian, UserDto> entityConverter;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final lImageService photoService;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;


    @Override
    public List<UserDto> getAllVeterinariansWithDetails(){
        List<Veterinarian> veterinarians = userRepository.findAllByUserType("VET");
        return veterinarians.stream()
                .map(this ::mapVeterinarianToUserDto)
                .toList();
    }

    @Override
    public List<UserDto> findAvailableVetsForAppointment(String specialization, LocalDate date, LocalTime time){
        List<Veterinarian> filteredVets = getAvailableVeterinarians(specialization, date, time);
        return  filteredVets.stream()
                .map(this ::mapVeterinarianToUserDto)
                .toList();
    }

    @Override
    public List<Veterinarian> getVeterinariansBySpecialization(String specialization) {
        if(!veterinarianRepository.existsBySpecialization(specialization)){
            throw new ResourceNotFoundException("No veterinarian found with" +specialization +" in the system");
        }
        return veterinarianRepository.findBySpecialization(specialization);

    }

    @Override
    public UserDto mapVeterinarianToUserDto(Veterinarian veterinarian) {
        UserDto userDto = entityConverter.mapEntityToDto(veterinarian, UserDto.class);
        double averageRating = reviewService.getAverageRatingForVet(veterinarian.getId());
        Long totalReviewer = reviewRepository.countByVeterinarianId(veterinarian.getId());
        userDto.setAverageRating(averageRating);
        userDto.setTotalReviewers(totalReviewer);
        if(veterinarian.getPhoto() != null){
            try {
                byte[] photoBytes = photoService.getImageData(veterinarian.getPhoto().getId());
                userDto.setPhoto(photoBytes);
            } catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return userDto;
    }


    @Override
    public List<Veterinarian> getAvailableVeterinarians(String specialization, LocalDate date, LocalTime time){
        List<Veterinarian> veterinarians = getVeterinariansBySpecialization(specialization);
        return veterinarians.stream()
                .filter(vet -> isVetAvailable(vet, date, time))
                .toList();

    }

    @Override
    public boolean isVetAvailable(Veterinarian veterinarian, LocalDate requestedDate, LocalTime requestedTime){
        if(requestedDate != null && requestedTime != null){
            LocalTime requestedEndTime = requestedTime.plusHours(2);
            return appointmentRepository.findByVeterinarianAndAppointmentDate(veterinarian, requestedDate)
                    .stream()
                    .noneMatch(existingAppointment -> doesAppointmentOverLap((Appointment) existingAppointment, requestedTime, requestedEndTime));
        }
        return true;
    }

    @Override
    public boolean doesAppointmentOverLap(Appointment existingAppointment, LocalTime requestedStartTime, LocalTime requestedEndTime){
        LocalTime existingStartTime = existingAppointment.getAppointmentTime();
        LocalTime existingEndTime = existingStartTime.plusHours(2);
        LocalTime unavailableStartTime = existingStartTime.minusHours(1);
        LocalTime unavailableEndTime = existingEndTime.plusMinutes(170);
        return !requestedStartTime.isBefore(unavailableStartTime) && !requestedEndTime.isAfter(unavailableEndTime);
    }
}
