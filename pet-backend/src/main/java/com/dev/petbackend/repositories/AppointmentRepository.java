package com.dev.petbackend.repositories;

import com.dev.petbackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);
}
