package com.dev.petbackend.model.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    private String appointmentDate;
    private String appointmentTime;
    private String reason;


}
