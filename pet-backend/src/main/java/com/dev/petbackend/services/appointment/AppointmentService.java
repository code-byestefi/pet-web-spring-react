package com.dev.petbackend.services.appointment;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.AppointmentDto;
import com.dev.petbackend.model.dto.EntityConverter;
import com.dev.petbackend.model.dto.PetDto;
import com.dev.petbackend.model.dto.request.AppointmentUpdateRequest;
import com.dev.petbackend.model.dto.request.BookAppointmentRequest;
import com.dev.petbackend.model.enums.AppointmentStatus;
import com.dev.petbackend.model.factory.Pet;
import com.dev.petbackend.repositories.AppointmentRepository;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.services.pet.lPetService;
import com.dev.petbackend.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService implements lAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final lPetService petService;
    private final EntityConverter<Appointment, AppointmentDto> entityConverter;
    private final EntityConverter<Pet, PetDto> petEntityConverter;

    @Transactional
    @Override
    public Appointment createAppointment(BookAppointmentRequest request, Long senderId, Long recipientId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);
        if (sender.isPresent() && recipient.isPresent()) {
            Appointment appointment = request.getAppointment();
            List<Pet> pets = request.getPets();
            pets.forEach(pet ->pet.setAppointment(appointment));
            List<Pet> savedPets = petService.savePetForAppointment(pets);
            appointment.setPets(savedPets);

            appointment.addPatient(sender.get());
            appointment.addVeterinarian(recipient.get());
            appointment.setAppointmentNo();
            appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
            return appointmentRepository.save(appointment);
        }
        throw new ResourceNotFoundException(FeedBackMessage.SENDER_RECIPIENT_NOT_FOUND);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment existingAppointment = getAppointmentById(id);
        if(!Objects.equals(existingAppointment.getStatus(), AppointmentStatus.WAITING_FOR_APPROVAL)) {
            throw new IllegalStateException(FeedBackMessage.ALREADY_APPROVED) ;
        }
        existingAppointment.setAppointmentDate(LocalDate.parse(request.getAppointmentDate()));
        existingAppointment.setAppointmentTime(LocalTime.parse(request.getAppointmentTime()));
        existingAppointment.setReason(request.getReason());
        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.findById(id)
                .ifPresentOrElse(appointmentRepository::delete, ()->{
                    throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
                });

    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.NOT_FOUND));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepository.findByAppointmentNo(appointmentNo);
    }

    @Override
    public List<AppointmentDto> getUserAppointments(Long userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
        return appointments.stream()
                .map(appointment -> {
                    AppointmentDto appointmentDto = entityConverter.mapEntityToDto(appointment, AppointmentDto.class);
                    List<PetDto> petDto = appointment.getPets()
                            .stream()
                            .map(pet -> petEntityConverter.mapEntityToDto(pet, PetDto.class)).toList();
                    appointmentDto.setPets(petDto);
                    return appointmentDto;
                }).toList();
    }
}
