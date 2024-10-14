package com.dev.petbackend.model.dto.request;

import com.dev.petbackend.model.Appointment;
import com.dev.petbackend.model.factory.Pet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookAppointmentRequest {

    private Appointment appointment;
    private List<Pet> pets;

}
