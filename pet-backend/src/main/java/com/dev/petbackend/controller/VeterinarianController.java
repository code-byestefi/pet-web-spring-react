package com.dev.petbackend.controller;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.model.dto.response.ApiResponse;
import com.dev.petbackend.services.appointment.lAppointmentService;
import com.dev.petbackend.services.veterinarian.lVeterinarianService;
import com.dev.petbackend.utils.FeedBackMessage;
import com.dev.petbackend.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping(UrlMapping.VETERINARIANS)
@RequiredArgsConstructor
public class VeterinarianController {
    private final lVeterinarianService veterinarianService;
    private final lAppointmentService appointmentService;

    @GetMapping(UrlMapping.GET_ALL_VETERINARIANS)
    public ResponseEntity<ApiResponse> getAllVeterinarians(){
        List<UserDto> allVeterinarians = veterinarianService.getAllVeterinariansWithDetails();
        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND,allVeterinarians));

    }

    @GetMapping(UrlMapping.SEARCH_VETERINARIAN_FOR_APPOINTMENT)
    public ResponseEntity<ApiResponse> searchVeterinariansForAppointment(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime time,
            @RequestParam String specialization){

        try {
            List<UserDto> availableVeterinarians = veterinarianService.findAvailableVetsForAppointment(specialization, date, time);
            if(availableVeterinarians.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(FeedBackMessage.NO_VETS_AVAILABLE, null));
            }
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND,availableVeterinarians));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
