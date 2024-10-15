package com.dev.petbackend.repositories;

import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);

    boolean existsByVeterinarianIdAndPatientIdAndStatus(Long veterinarianId, Long reviewerId, AppointmentStatus appointmentStatus);
}
