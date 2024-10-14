package com.dev.petbackend.services.appointment;

import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.dto.request.AppointmentUpdateRequest;

import java.util.List;

public interface lAppointmentService {

    Appointment createAppointment(Appointment appointment, Long sender, Long recipient);
    List<Appointment> getAllAppointments();
    Appointment updateAppointment(Long id, AppointmentUpdateRequest request);

    void deleteAppointment(Long id);
    Appointment getAppointmentById(Long id);
    Appointment getAppointmentByNo(String appointmentNo);

}
